from enum import Enum

try:
    from enum import StrEnum
except ImportError:
    # Python < 3.11
    class StrEnum(str, Enum):
        """Enum where members are also instances of str."""
        pass

class Configuration(StrEnum):
    """Constantes del módulo Convive JWT"""
    NAME_MODULO = 'convive_jwt'
    ALGORITHM_RS = 'RS256'
    DECODE = 'utf-8'

class Endpoints(StrEnum):
    """Rutas de endpoints para la API de Convive"""
    AUTH_TOKEN = '/api/convive/auth/token'
    AUTH_REGISTER = '/api/convive/auth/register'
    AUTH_REFRESH = '/api/convive/auth/refresh'
    AUTH_LOGOUT = '/api/convive/auth/logout'
    USER_ME = '/api/convive/users/me'

    @property
    def path(self):
        return self.value

# Token Expiration Times (in seconds)
# Tiempos ajustados para la aplicación ConVive
ACCESS_TOKEN_EXPIRES_IN = 3600  # 1 hora
LOGIN_TOKEN_EXPIRES_IN = 3600   # 1 hora
REFRESH_TOKEN_EXPIRES_IN = 2592000  # 30 días

# Token Types
TOKEN_TYPE_ACCESS = 'access'
TOKEN_TYPE_LOGIN = 'login'
TOKEN_TYPE_REFRESH = 'refresh'
