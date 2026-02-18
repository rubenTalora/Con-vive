# -*- coding: utf-8 -*-

from odoo import models, fields, api


class ConviveRefreshToken(models.Model):
    """Modelo para almacenar refresh tokens de ConVive de forma segura"""
    _name = 'convive.refresh.token'
    _description = 'Convive Refresh Token'
    _order = 'create_date desc'

    user_id = fields.Many2one('res.users', string='Usuario', required=True, ondelete='cascade', index=True)
    token_hash = fields.Char(string='Hash del Token', required=True, copy=False, index=True, readonly=True,
                             help='Hash SHA256 del refresh token para almacenamiento seguro')
    expiration_date = fields.Datetime(string='Fecha de Expiración', required=True, index=True)
    is_revoked = fields.Boolean(string='Revocado', default=False, index=True,
                                 help='Indica si el token ha sido revocado manualmente')
    issued_at = fields.Datetime(string='Emitido en', required=True, default=fields.Datetime.now)

    _sql_constraints = [
        ('token_hash_unique', 'unique(token_hash)', 'El hash del token debe ser único!'),
    ]

    def name_get(self):
        """Formato de visualización del nombre del token"""
        result = []
        for token in self:
            name = f"Token de {token.user_id.login} - {token.issued_at.strftime('%Y-%m-%d %H:%M')}"
            if token.is_revoked:
                name += " (Revocado)"
            result.append((token.id, name))
        return result

    @api.model
    def clean_expired_tokens(self):
        """Método para limpiar tokens expirados de la base de datos (puede ejecutarse por cron)"""
        expired_tokens = self.search([
            ('expiration_date', '<', fields.Datetime.now())
        ])
        count = len(expired_tokens)
        expired_tokens.unlink()
        return count
