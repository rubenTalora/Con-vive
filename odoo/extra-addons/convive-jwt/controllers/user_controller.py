from odoo import http

from ..services.auth_service import AuthService
from ..utils.http import json_response

from ..constants import Endpoints

class ConviveUserController(http.Controller):
    """Controlador de usuarios para la API REST de ConVive"""
    
    @http.route(Endpoints.USER_ME, type='http', auth='none', csrf=False, cors='*', methods=['GET'])
    def get_user_data(self, **kw):
        """Devuelve los datos del usuario autenticado mediante JWT en ConVive."""
        user = AuthService.get_user_from_request()

        if not user:
            return json_response(
                {
                    "error": "No autorizado",
                    "details": "Token inválido o expirado"
                },
                status=401
            )

        # Obtener información adicional del usuario
        partner = user.partner_id
        
        user_data = {
            "id": user.id,
            "name": user.name,
            "login": user.login,
            "email": partner.email if partner and partner.email else user.login,
            "partner_id": partner.id if partner else None,
            "role": getattr(user, 'user_role', 'user'),  # Rol almacenado en el request
            "birth_date": user.birth_date.isoformat() if user.birth_date else None,
        }

        return json_response(user_data, status=200)
