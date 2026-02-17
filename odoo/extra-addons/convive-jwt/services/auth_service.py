import logging
import odoo

from odoo.http import request
from odoo.tools import config
from odoo.exceptions import AccessDenied, UserError

from ..services.jwt_service import JWTService
from ..constants import ACCESS_TOKEN_EXPIRES_IN, LOGIN_TOKEN_EXPIRES_IN, TOKEN_TYPE_ACCESS, TOKEN_TYPE_LOGIN, REFRESH_TOKEN_EXPIRES_IN, TOKEN_TYPE_REFRESH

logger = logging.getLogger(__name__)

class AuthService:
    """Servicio de autenticación para la aplicación ConVive"""

    @staticmethod
    def authenticate(login, password):
        """Autentica usuario en Odoo y devuelve JWTs de acceso y refresh para ConVive."""
        db = request.session.db

        try:
            uid = request.session.authenticate(db, login, password)
        except AccessDenied:
            logger.info(f"Credenciales inválidas para login: {login}")
            return None
        
        if not uid:
            logger.info(f"Autenticación fallida para login: {login}")
            return None
        
        user = request.env["res.users"].sudo().browse(uid)
        
        # Determinar rol del usuario en ConVive
        role = "admin" if user.has_group("base.group_system") else "user"
        
        logger.info(f"Usuario {login} (ID: {uid}) autenticado exitosamente en ConVive con rol: {role}")
        logger.info(f"Grupos del usuario: {[g.name for g in user.groups_id]}")
        
        # Crear refresh token
        refresh_token = JWTService.create_refresh_token(
            user_id=uid,
            login=login,
            role=role
        )

        # Crear access token
        access_token = JWTService.create_token(
            payload={
                "sub": str(uid),
                "login": login,
                "role": role,
                "name": user.name,
            },
            token_type=TOKEN_TYPE_ACCESS,
            expires_in=ACCESS_TOKEN_EXPIRES_IN
        )

        return {
            "access_token": access_token,
            "refresh_token": refresh_token,
            "token_type": "Bearer",
            "expires_in": ACCESS_TOKEN_EXPIRES_IN,
            "user": {
                "id": uid,
                "name": user.name,
                "login": user.login,
                "role": role
            }
        }
    
    @staticmethod
    def refresh_token_flow(refresh_token):
        """
        Maneja el flujo de refresh token en ConVive: valida el token existente y emite uno nuevo de acceso.
        """
        user_id = JWTService.validate_refresh_token(refresh_token)

        if not user_id:
            logger.warning("Refresh token inválido o expirado")
            return None
        
        user = request.env["res.users"].sudo().browse(user_id)
        if not user.exists():
            logger.warning(f"Usuario {user_id} no encontrado para refresh token válido.")
            return None
        
        role = "admin" if user.has_group("base.group_system") else "user"

        new_access_token = JWTService.create_token(
            payload={
                "sub": str(user_id),
                "login": user.login,
                "role": role,
                "name": user.name,
            },
            token_type=TOKEN_TYPE_ACCESS,
            expires_in=ACCESS_TOKEN_EXPIRES_IN
        )

        logger.info(f"Access token renovado exitosamente para usuario {user_id}")

        return {
            "access_token": new_access_token,
            "refresh_token": refresh_token,
            "token_type": "Bearer",
            "expires_in": ACCESS_TOKEN_EXPIRES_IN
        }

    @staticmethod
    def get_user_from_request():
        """Devuelve el usuario autenticado a partir del JWT del header en ConVive."""
        auth_header = request.httprequest.headers.get("Authorization")

        if not auth_header or not auth_header.startswith("Bearer "):
            return None
        
        token = auth_header.split(" ", 1)[1]

        try:
            payload = JWTService.decode_token(token)
            
            if payload.get("type") != TOKEN_TYPE_ACCESS:
                logger.warning("Tipo de token inválido para acceso: %s", payload.get("type"))
                return None

            uid = payload.get("sub")
            if not uid:
                return None
        
            user = request.env["res.users"].sudo().browse(int(uid))
            if not user.exists():
                return None
            
            current_role = "admin" if user.has_group("base.group_system") else "user"
            
            # Almacenar el rol en el request para uso posterior
            request.user_role = current_role
            
            return user
        
        except Exception as e:
            logger.warning("JWT inválido en ConVive: %s", e)
            return None
        
    @staticmethod
    def get_user_data_with_role():
        """Devuelve el usuario autenticado con su rol actual desde la base de datos de ConVive."""
        user = AuthService.get_user_from_request()
        
        if not user:
            return None
        
        role = "admin" if user.has_group("base.group_system") else "user"
        
        return {
            "id": user.id,
            "name": user.name,
            "login": user.login,
            "email": user.partner_id.email if user.partner_id and user.partner_id.email else user.login,
            "role": role,
            "partner_id": user.partner_id.id if user.partner_id else None,
        }
        
    @staticmethod
    def register_user(name, login, password, email=None):
        """Crea un usuario en Odoo para la aplicación ConVive y devuelve tokens JWT."""

        existing_user = request.env['res.users'].sudo().search([('login', '=', login)], limit=1)
        company = request.env['res.company'].sudo().search([], limit=1)
        
        if existing_user:
            logger.warning(f"Intento de registro con login existente: {login}")
            return {"ok": False, "error": "El usuario ya existe"}
        
        try:
            env_as_superuser = request.env(user=odoo.SUPERUSER_ID, su=True)

            # Grupo portal por defecto para usuarios de ConVive
            portal_group_id = env_as_superuser.ref('base.group_portal').id

            user_vals = {
                'login': login,
                'name': name,
                'password': password,
                'email': email or login,
                'company_id': company.id,
                'company_ids': [(6, 0, [company.id])],
                'groups_id': [(6, 0, [portal_group_id])]
            }

            new_user = env_as_superuser['res.users'].create(user_vals)
            
            logger.info(f"Usuario {login} (ID: {new_user.id}) registrado exitosamente en ConVive")
            
        except UserError as e:
            logger.error("Error al crear el usuario en ConVive: %s", e)
            return {"ok": False, "error": str(e)}
        except Exception as e:
            logger.error("Error inesperado al registrar usuario: %s", e)
            return {"ok": False, "error": "Error al crear el usuario"}
        
        # Generar tokens para el nuevo usuario
        refresh_token = JWTService.create_refresh_token(
            user_id=new_user.id,
            login=new_user.login,
            role="user"
        )

        access_token = JWTService.create_token(
            payload={
                "sub": str(new_user.id),
                "login": new_user.login,
                "role": "user",
                "name": new_user.name,
            },
            token_type=TOKEN_TYPE_ACCESS,
            expires_in=ACCESS_TOKEN_EXPIRES_IN
        )

        token_data = {
            "access_token": access_token,
            "refresh_token": refresh_token,
            "token_type": "Bearer",
            "expires_in": ACCESS_TOKEN_EXPIRES_IN,
            "user": {
                "id": new_user.id,
                "name": new_user.name,
                "login": new_user.login,
                "role": "user"
            }
        }

        return {"ok": True, "data": token_data}
