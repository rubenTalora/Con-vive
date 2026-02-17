# -*- coding: utf-8 -*-
# Este archivo se mantiene por compatibilidad, los modelos principales están en convive_jwt_models.py

from odoo import models, fields, api


class JWTTokenLegacy(models.Model):
    """Modelo legacy de JWT Token - mantenido por compatibilidad"""
    _name = 'convive.jwt.token.legacy'
    _description = 'JWT Token Legacy'
    _order = 'create_date desc'

    name = fields.Char(string='Token Name', required=True)
    user_id = fields.Many2one('res.users', string='User', required=True, default=lambda self: self.env.user)
    token = fields.Text(string='Token', readonly=True)
    expiration_date = fields.Datetime(string='Expiration Date')
    active = fields.Boolean(string='Active', default=True)
    description = fields.Text(string='Description')
    
    @api.model
    def generate_token(self):
        """Placeholder method for JWT token generation"""
        # TODO: Implement JWT token generation
        pass
    
    def revoke_token(self):
        """Revoke JWT token"""
        self.write({'active': False})
        return True
