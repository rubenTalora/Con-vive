{
    'name': 'Convive JWT',
    'version': '17.0.1.0.0',
    'category': 'Authentication',
    'summary': 'JWT Authentication for Convive',
    'description': """
        JWT Authentication Module
        =========================
        This module provides JWT authentication capabilities for Odoo.
    """,
    'author': 'Convive',
    'website': '',
    'depends': ['base'],
    'data': [
        'security/ir.model.access.csv',
        'views/jwt_token_views.xml',
    ],
    'installable': True,
    'application': True,
    'auto_install': False,
    'license': 'LGPL-3',
}
