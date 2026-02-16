# -*- coding: utf-8 -*-

from odoo import models, fields, api


class JWTToken(models.Model):
    _name = 'convive.jwt.token'
    _description = 'JWT Token'
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
