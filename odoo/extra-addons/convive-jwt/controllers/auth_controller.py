from odoo import http

from ..services.auth_service import AuthService
from ..utils.http import json_response, get_json_body

from ..constants import Endpoints


class ConviveAuthController(http.Controller):
    """Controlador de autenticación para la API REST de ConVive"""

    @http.route(Endpoints.AUTH_TOKEN, type='http', auth='none', csrf=False, cors='*', methods=['POST'])
    def authenticate(self, **kw):
        """Autentica un usuario de ConVive y devuelve tokens JWT de acceso y refresh."""

        data = get_json_body()
        if not data:
            return json_response(
                {"error": "JSON inválido o cuerpo vacío"},
                status=400)
        
        login = data.get("login")
        password = data.get("password")

        if not login or not password:
            return json_response(
                {"error": "Credenciales faltantes (login y password requeridos)"},
                status=400)
        
        token_data = AuthService.authenticate(login, password)

        if not token_data:
            return json_response(
                {"error": "Credenciales inválidas"},
                status=401)

        return json_response(token_data, status=200)
    
    @http.route(Endpoints.AUTH_REFRESH, type='http', auth='none', csrf=False, cors='*', methods=['POST'])
    def refresh_token(self, **kw):
        """Refresca un access token utilizando un refresh token válido de ConVive."""
        data = get_json_body()
        if not data:
            return json_response(
                {"error": "JSON inválido o cuerpo vacío"},
                status=400)
        
        refresh_token = data.get("refresh_token")

        if not refresh_token:
            return json_response(
                {"error": "refresh_token faltante"},
                status=400)
        
        token_data = AuthService.refresh_token_flow(refresh_token)

        if not token_data:
            return json_response(
                {"error": "Refresh token inválido o expirado"},
                status=401)

        return json_response(token_data, status=200)

    @http.route(Endpoints.AUTH_REGISTER, type='http', auth='none', csrf=False, cors='*', methods=['POST'])
    def register(self, **kw):
        """Registra una nueva cuenta de usuario en ConVive"""
        
        data = get_json_body()
        if not data:
            return json_response({
                "error": "JSON inválido o cuerpo vacío"
            }, status=400)

        login = data.get('login')
        password = data.get('password')
        name = data.get('name')
        email = data.get('email')

        if not login or not password or not name:
            return json_response(
                {
                    "error": "Campos requeridos faltantes (name, login, password)"
                }, status=400)
        
        result = AuthService.register_user(name, login, password, email)

        if not result["ok"]:
            return json_response({
                "error": result["error"]
            }, status=400)

        return json_response(result["data"], status=201)
