# -*- coding: utf-8 -*-

from odoo import models, fields, api


class ConviveSubscription(models.Model):
    """Modelo para gestionar tipos de suscripciones de ConVive"""
    _name = 'convive.subscription'
    _description = 'Suscripción ConVive'
    _order = 'name'

    name = fields.Char(string='Nombre del Plan', required=True, help='Nombre del tipo de suscripción (ej: Basic, Premium, VIP)')
    description = fields.Text(string='Descripción', help='Descripción detallada de las características del plan')
    price = fields.Float(string='Precio', required=True, help='Precio mensual de la suscripción')
    duration_days = fields.Integer(string='Duración (Días)', required=True, default=30, help='Duración del periodo de suscripción en días')
    active = fields.Boolean(string='Activo', default=True, help='Si está activo, el plan puede asignarse a usuarios')
    
    # Características del plan
    max_groups = fields.Integer(string='Grupos Máximos', default=0, help='Número máximo de grupos que puede crear/unirse')
    max_publications = fields.Integer(string='Publicaciones Máximas', default=0, help='Número máximo de publicaciones que puede crear')
    features = fields.Text(string='Características', help='Lista de características adicionales del plan')
    
    # Relación con usuarios que tienen este plan
    user_subscription_ids = fields.One2many('convive.user.subscription', 'subscription_id', string='Usuarios Suscritos')
    subscriber_count = fields.Integer(string='Número de Suscriptores', compute='_compute_subscriber_count', store=True)
    
    @api.depends('user_subscription_ids')
    def _compute_subscriber_count(self):
        """Calcula el número de usuarios actualmente suscritos a este plan"""
        today = fields.Date.today()
        for record in self:
            active_subs = record.user_subscription_ids.filtered(
                lambda s: s.end_date and s.end_date >= today
            )
            record.subscriber_count = len(active_subs)
    
    _sql_constraints = [
        ('name_unique', 'unique(name)', 'El nombre del plan de suscripción debe ser único.'),
    ]
