from odoo import http, fields

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

    @http.route(Endpoints.SUBSCRIPTION_CONSUME_PUB, type='http', auth='none', csrf=False, cors='*', methods=['POST'])
    def consume_publication(self, **kw):
        """
        Consume una publicación del cupo de suscripción del usuario autenticado.
        Devuelve 403 si no tiene suscripción activa o si alcanzó el límite.
        """
        user = AuthService.get_user_from_request()
        if not user:
            return json_response({"error": "No autorizado"}, status=401)

        today = fields.Date.today()
        active_sub_records = user.convive_subscription_ids.filtered(
            lambda s: s.end_date and s.end_date >= today
        )

        if not active_sub_records:
            return json_response({"error": "Sin suscripción activa"}, status=403)

        # Tomar la suscripción que caduca más tarde
        active_sub_record = max(active_sub_records, key=lambda s: s.end_date)
        max_pub = active_sub_record.subscription_id.max_publications
        used = active_sub_record.publications_used

        # max_publications == 0 significa ilimitado
        if max_pub > 0 and used >= max_pub:
            return json_response({
                "error": "Límite de publicaciones de la suscripción alcanzado",
                "max_publications": max_pub,
                "publications_used": used,
                "publications_remaining": 0,
            }, status=403)

        # Incrementar contador
        active_sub_record.sudo().write({'publications_used': used + 1})

        remaining = (max_pub - (used + 1)) if max_pub > 0 else -1  # -1 = ilimitado
        return json_response({
            "ok": True,
            "publications_used": used + 1,
            "publications_remaining": remaining,
            "unlimited": max_pub == 0,
        }, status=200)
