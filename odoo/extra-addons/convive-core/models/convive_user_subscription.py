# -*- coding: utf-8 -*-

from odoo import models, fields, api
from datetime import timedelta


class ConviveUserSubscription(models.Model):
    """Modelo que relaciona usuarios con sus suscripciones (historial)"""
    _name = 'convive.user.subscription'
    _description = 'Suscripción de Usuario ConVive'
    _order = 'start_date desc'
    _rec_name = 'subscription_id'

    user_id = fields.Many2one('res.users', string='Usuario', required=True, ondelete='cascade', index=True)
    subscription_id = fields.Many2one('convive.subscription', string='Plan de Suscripción', required=True, ondelete='restrict')
    start_date = fields.Date(string='Fecha de Inicio', default=fields.Date.today, required=True, readonly=True)
    end_date = fields.Date(string='Fecha de Caducidad', compute='_compute_end_date', store=True, readonly=True, index=True)
    is_active = fields.Boolean(string='Activa', compute='_compute_is_active', store=True)
    duration_days = fields.Integer(string='Duración (días)', related='subscription_id.duration_days', readonly=True)
    
    # Información adicional
    notes = fields.Text(string='Notas')
    created_by = fields.Many2one('res.users', string='Creado por', default=lambda self: self.env.user, readonly=True)
    
    @api.depends('start_date', 'subscription_id.duration_days')
    def _compute_end_date(self):
        """Calcula la fecha de caducidad sumando la duración del plan a la fecha de inicio"""
        for record in self:
            if record.start_date and record.subscription_id and record.subscription_id.duration_days:
                record.end_date = record.start_date + timedelta(days=record.subscription_id.duration_days)
            else:
                record.end_date = record.start_date
    
    @api.depends('end_date')
    def _compute_is_active(self):
        """Determina si la suscripción está activa (no ha caducado)"""
        today = fields.Date.today()
        for record in self:
            record.is_active = record.end_date and record.end_date >= today
    
    def name_get(self):
        """Formato personalizado para mostrar la suscripción"""
        result = []
        for record in self:
            name = f"{record.subscription_id.name} - {record.user_id.name}"
            if record.is_active:
                name += " (Activa)"
            else:
                name += " (Expirada)"
            result.append((record.id, name))
        return result
