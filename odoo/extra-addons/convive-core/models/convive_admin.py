# -*- coding: utf-8 -*-

from odoo import models, fields, api, exceptions


class ConviveAdmin(models.Model):
    """Modelo para gestionar administradores de ConVive"""
    _name = 'convive.admin'
    _description = 'Administrador ConVive'
    _inherit = ['mail.thread', 'mail.activity.mixin']
    _order = 'create_date desc'

    user_id = fields.Many2one('res.users', string='Usuario', required=True, ondelete='cascade', index=True)
    name = fields.Char(string='Nombre', related='user_id.name', readonly=True, store=True)
    email = fields.Char(string='Email', related='user_id.login', readonly=True, store=True)
    
    admin_level = fields.Selection([
        ('moderator', 'Moderador'),
        ('admin', 'Administrador'),
        ('super_admin', 'Super Administrador')
    ], string='Nivel de Administración', required=True, default='moderator', help='Nivel de permisos del administrador', tracking=True)
    
    active = fields.Boolean(string='Activo', default=True, help='Si está inactivo, pierde privilegios de administrador', tracking=True)
    assigned_date = fields.Date(string='Fecha de Asignación', default=fields.Date.today, readonly=True)
    assigned_by = fields.Many2one('res.users', string='Asignado por', default=lambda self: self.env.user, readonly=True)
    notes = fields.Text(string='Notas')
    
    # Permisos específicos
    can_manage_users = fields.Boolean(string='Puede Gestionar Usuarios', default=True)
    can_manage_subscriptions = fields.Boolean(string='Puede Gestionar Suscripciones', default=True)
    can_manage_content = fields.Boolean(string='Puede Gestionar Contenido', default=True)
    can_manage_admins = fields.Boolean(string='Puede Gestionar Administradores', default=False, help='Solo super admins deberían tener este permiso')
    
    _sql_constraints = [
        ('user_unique', 'unique(user_id)', 'Este usuario ya es administrador.'),
    ]
    
    @api.model
    def create(self, vals):
        """
        Al crear un admin:
        - Si ya existe un registro inactivo para el usuario, reactivarlo
        - Si no existe, crear uno nuevo
        - Asignar grupos de Odoo correspondientes
        """
        user_id = vals.get('user_id')
        if user_id:
            # Buscar si existe un admin inactivo para este usuario
            existing_admin = self.with_context(active_test=False).search([
                ('user_id', '=', user_id),
                ('active', '=', False)
            ], limit=1)
            
            if existing_admin:
                # Reactivar el admin existente y actualizar sus valores
                existing_admin.write({
                    'active': True,
                    'admin_level': vals.get('admin_level', existing_admin.admin_level),
                    'can_manage_users': vals.get('can_manage_users', existing_admin.can_manage_users),
                    'can_manage_subscriptions': vals.get('can_manage_subscriptions', existing_admin.can_manage_subscriptions),
                    'can_manage_content': vals.get('can_manage_content', existing_admin.can_manage_content),
                    'can_manage_admins': vals.get('can_manage_admins', existing_admin.can_manage_admins),
                    'notes': vals.get('notes', existing_admin.notes),
                    'assigned_by': self.env.user.id,
                })
                return existing_admin
        
        # Si no existe un admin inactivo, crear uno nuevo
        admin = super(ConviveAdmin, self).create(vals)
        admin._grant_admin_groups()
        return admin
    
    def write(self, vals):
        """Al modificar, actualizar grupos si cambia el estado activo o nivel"""
        res = super(ConviveAdmin, self).write(vals)
        if 'active' in vals or 'admin_level' in vals:
            for admin in self:
                if admin.active:
                    admin._grant_admin_groups()
                else:
                    admin._revoke_admin_groups()
        return res
    
    def unlink(self):
        """Al eliminar, revocar permisos de administrador"""
        for admin in self:
            admin._revoke_admin_groups()
        return super(ConviveAdmin, self).unlink()
    
    def _grant_admin_groups(self):
        """Otorga grupos de Odoo según el nivel de administrador"""
        self.ensure_one()
        internal_user_group = self.env.ref('base.group_user')
        portal_group = self.env.ref('base.group_portal')
        
        # Remover de portal y agregar a usuarios internos
        self.user_id.write({
            'groups_id': [
                (3, portal_group.id),  # Remove portal
                (4, internal_user_group.id),  # Add internal user
            ]
        })
        
        # Si es super admin, agregar grupo de sistema
        if self.admin_level == 'super_admin':
            system_group = self.env.ref('base.group_system')
            self.user_id.write({
                'groups_id': [(4, system_group.id)]
            })
    
    def _revoke_admin_groups(self):
        """Revoca grupos administrativos de Odoo"""
        self.ensure_one()
        internal_user_group = self.env.ref('base.group_user')
        system_group = self.env.ref('base.group_system')
        portal_group = self.env.ref('base.group_portal')
        
        # Volver a usuario portal
        self.user_id.write({
            'groups_id': [
                (3, internal_user_group.id),
                (3, system_group.id),
                (4, portal_group.id),
            ]
        })
    
    def name_get(self):
        """Formato personalizado para mostrar el administrador"""
        result = []
        for record in self:
            level_dict = dict(self._fields['admin_level'].selection)
            level = level_dict.get(record.admin_level, '')
            name = f"{record.name} ({level})"
            if not record.active:
                name += " [Inactivo]"
            result.append((record.id, name))
        return result
