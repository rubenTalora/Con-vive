# -*- coding: utf-8 -*-

from odoo import models, fields, api


class ResUsers(models.Model):
    """Extensión del modelo de usuarios para ConVive"""
    _inherit = 'res.users'
    
    # Redefinir active con valor por defecto True
    active = fields.Boolean(default=True)
    
    # Información personal
    birth_date = fields.Date(string='Fecha de Nacimiento', help='Fecha de nacimiento del usuario')
    
    # Relaciones con suscripciones
    convive_subscription_ids = fields.One2many('convive.user.subscription', 'user_id', string='Historial de Suscripciones')
    active_subscription_id = fields.Many2one('convive.subscription', string='Suscripción Activa', compute='_compute_active_subscription', store=True)
    subscription_expiry_date = fields.Date(string='Fecha de Caducidad', compute='_compute_subscription_expiry', store=True, readonly=True, index=True)
    has_active_subscription = fields.Boolean(string='Tiene Suscripción Activa', compute='_compute_has_active_subscription', store=True)
    days_until_expiry = fields.Integer(string='Días Restantes', compute='_compute_days_until_expiry', store=False)
    subscription_display = fields.Char(string='Plan Activo', compute='_compute_subscription_display', store=False)
    
    # Campo para asignar nueva suscripción
    new_subscription_id = fields.Many2one('convive.subscription', string='Asignar Nueva Suscripción', help='Selecciona un plan para asignar al usuario')
    
    # Información de administrador
    is_convive_admin = fields.Boolean(string='¿Es Admin de ConVive?', compute='_compute_is_convive_admin', store=True)
    convive_admin_id = fields.One2many('convive.admin', 'user_id', string='Registro de Admin')
    
    # Campo de contraseña visible (solo para mostrar en tree, no almacena nada)
    password_visible = fields.Char(string='Contraseña', compute='_compute_password_visible', inverse='_set_password_visible', store=False)
    
    @api.depends('password')
    def _compute_password_visible(self):
        """Mostrar la contraseña como asteriscos en la lista"""
        for record in self:
            if record.password:
                record.password_visible = '********'
            else:
                record.password_visible = ''
    
    def _set_password_visible(self):
        """No hace nada, el password se maneja por separado"""
        pass
    
    @api.onchange('login')
    def _onchange_login(self):
        """Sincronizar el email cuando se cambia el login"""
        if self.login:
            self.email = self.login
    
    # Configuración de login: usar email como login
    @api.model
    def create(self, vals):
        """Al crear un usuario, asegurar que email y login estén sincronizados"""
        # El login es obligatorio, usar como email si no está presente
        if 'login' in vals and 'email' not in vals:
            vals['email'] = vals['login']
        
        # Si se asigna suscripción en la creación, procesarla después
        new_subscription_id = vals.pop('new_subscription_id', False)
        
        user = super(ResUsers, self).create(vals)
        
        # Asignar suscripción si se especificó
        if new_subscription_id:
            self.env['convive.user.subscription'].sudo().create({
                'user_id': user.id,
                'subscription_id': new_subscription_id,
                'start_date': fields.Date.today(),
            })
        
        return user
    
    def write(self, vals):
        """
        Al modificar un usuario:
        - Si se asigna nueva suscripción, crear registro en historial
        - Sincronizar email y login
        """
        # Sincronizar email y login
        if 'login' in vals and 'email' not in vals:
            vals['email'] = vals['login']
        
        # Procesar nueva suscripción antes de write
        if 'new_subscription_id' in vals and vals.get('new_subscription_id'):
            subscription_id = vals['new_subscription_id']
            for user in self:
                # Crear nuevo registro de suscripción con sudo para evitar problemas de permisos
                self.env['convive.user.subscription'].sudo().create({
                    'user_id': user.id,
                    'subscription_id': subscription_id,
                    'start_date': fields.Date.today(),
                })
            # Limpiar el campo temporal para que no se almacene
            vals['new_subscription_id'] = False
        
        return super(ResUsers, self).write(vals)
    
    @api.depends('convive_subscription_ids.end_date', 'convive_subscription_ids.is_active')
    def _compute_active_subscription(self):
        """Obtiene la suscripción activa (la que no ha caducado)"""
        today = fields.Date.today()
        for record in self:
            active_sub = None
            # Buscar suscripciones válidas (no caducadas)
            valid_subs = record.convive_subscription_ids.filtered(
                lambda s: s.end_date and s.end_date >= today
            )
            if valid_subs:
                # Tomar la que caduca más tarde
                latest_sub = max(valid_subs, key=lambda s: s.end_date)
                active_sub = latest_sub.subscription_id
            record.active_subscription_id = active_sub
    
    @api.depends('convive_subscription_ids.end_date')
    def _compute_subscription_expiry(self):
        """Calcula la fecha de caducidad de la suscripción activa"""
        today = fields.Date.today()
        for record in self:
            expiry_date = None
            valid_subs = record.convive_subscription_ids.filtered(
                lambda s: s.end_date and s.end_date >= today
            )
            if valid_subs:
                # Tomar la fecha más lejana
                expiry_date = max(s.end_date for s in valid_subs)
            record.subscription_expiry_date = expiry_date
    
    @api.depends('active_subscription_id')
    def _compute_has_active_subscription(self):
        """Indica si el usuario tiene una suscripción activa"""
        for record in self:
            record.has_active_subscription = bool(record.active_subscription_id)
    
    @api.depends('subscription_expiry_date')
    def _compute_days_until_expiry(self):
        """Calcula los días restantes hasta el vencimiento de la suscripción"""
        today = fields.Date.today()
        for record in self:
            if record.subscription_expiry_date:
                delta = record.subscription_expiry_date - today
                record.days_until_expiry = delta.days
            else:
                record.days_until_expiry = 0
    
    @api.depends('active_subscription_id', 'has_active_subscription')
    def _compute_subscription_display(self):
        """Muestra el nombre del plan activo o 'Sin suscripción'"""
        for record in self:
            if record.active_subscription_id:
                record.subscription_display = record.active_subscription_id.name
            else:
                record.subscription_display = 'Sin suscripción'
    
    @api.depends('convive_admin_id', 'convive_admin_id.active')
    def _compute_is_convive_admin(self):
        """Verifica si el usuario es administrador de ConVive"""
        for record in self:
            admin = record.convive_admin_id.filtered(lambda a: a.active)
            record.is_convive_admin = bool(admin)
    
    def action_view_subscriptions(self):
        """Acción para ver el historial de suscripciones del usuario"""
        self.ensure_one()
        return {
            'type': 'ir.actions.act_window',
            'name': 'Historial de Suscripciones',
            'res_model': 'convive.user.subscription',
            'view_mode': 'tree,form',
            'domain': [('user_id', '=', self.id)],
            'context': {'default_user_id': self.id},
        }
