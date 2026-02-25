from odoo import models, fields

class ConviveWeb(models.Model):
    _name = 'ocnvive.web'
    _description = 'Modelo de Con!Vive Web'

    name = fields.Char(string='Nombre', required=True)
    description = fields.Text(string='Descripción')