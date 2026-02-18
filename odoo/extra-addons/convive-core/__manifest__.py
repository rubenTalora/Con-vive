{
    'name': 'ConVive Core',
    'version': '17.0.1.0.0',
    'category': 'ConVive',
    'summary': 'Módulo principal para gestión de usuarios y suscripciones de ConVive',
    'description': """
        ConVive Core Module
        ===================
        Módulo central de ConVive para la gestión de:
        
        * Usuarios con autenticación por email
        * Planes de suscripción configurables
        * Asignación y seguimiento de suscripciones
        * Gestión de administradores con niveles de acceso
        * Historial completo de suscripciones
        
        Características principales:
        -----------------------------
        * Login basado en correo electrónico
        * Control de suscripciones activas y fechas de caducidad
        * Tabla de administradores con permisos granulares
        * Interfaz administrativa completa
        * Compatible con módulo convive-jwt para API REST
    """,
    'author': 'ConVive Team',
    'website': '',
    'license': 'LGPL-3',
    'depends': ['base', 'mail'],
    'data': [
        'security/ir.model.access.csv',
        'views/convive_user_subscription_views.xml',
        'views/convive_subscription_views.xml',
        'views/convive_admin_views.xml',
        'views/res_users_views.xml',
        'views/menu_views.xml',
    ],
    'installable': True,
    'application': True,
    'auto_install': False,
}
