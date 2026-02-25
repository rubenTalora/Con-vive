import importlib.util
import os

from . import models
from . import controllers
from . import services

# Cargamos genera_keys.py por ruta porque el guión en 'convive-jwt'
# impide que Python resuelva imports relativos (from .genera_keys import ...).
_spec = importlib.util.spec_from_file_location(
    'convive_jwt_genera_keys',
    os.path.join(os.path.dirname(__file__), 'genera_keys.py'),
)
_genera_keys = importlib.util.module_from_spec(_spec)
_spec.loader.exec_module(_genera_keys)


def post_init_hook(env):
    _genera_keys.post_init_hook(env)
