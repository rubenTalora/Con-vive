{
    'name': 'Convive JWT Authentication',
    'version': '17.0.1.0.0',
    'category': 'Authentication',
    'summary': 'Sistema de autenticación JWT para la aplicación ConVive',
    'description': """
        Convive JWT Authentication Module
        ==================================
        Módulo de autenticación JWT completo para la aplicación ConVive.
        
        Características:
        * Autenticación mediante JWT con tokens de acceso y refresh
        * Registro de usuarios desde frontend externo
        * API REST completa para gestión de autenticación
        * Tokens firmados con algoritmo RS256
        * Gestión segura de refresh tokens en base de datos
        * Compatible con frontends externos (Vue, Flutter, React, etc.)
    """,
    'author': 'Convive Team',
    'website': '',
    'depends': ['base'],
    'external_dependencies': {'python': ['PyJWT']},
    'data': [
        'security/ir.model.access.csv',
        'views/jwt_token_views.xml',
    ],
    'installable': True,
    'application': True,
    'auto_install': False,
    'license': 'LGPL-3',
}
