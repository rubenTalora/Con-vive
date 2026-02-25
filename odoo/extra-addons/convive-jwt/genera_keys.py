import os
import logging

from odoo.tools import config

logger = logging.getLogger(__name__)


def post_init_hook(env):
    """Genera el par de claves RSA al instalar el módulo convive-jwt.
    
    Lee las rutas de claves desde odoo.conf (parámetros `private` y `public`).
    Si las claves ya existen, no las sobreescribe.
    """
    private_path = config.get("private")
    public_path = config.get("public")

    if not private_path or not public_path:
        logger.error(
            "[convive-jwt] No se encontraron las rutas de claves en odoo.conf. "
            "Añade 'private' y 'public' al archivo de configuración."
        )
        return

    # Crear directorios si no existen
    os.makedirs(os.path.dirname(private_path), exist_ok=True)
    os.makedirs(os.path.dirname(public_path), exist_ok=True)

    if os.path.exists(private_path) and os.path.exists(public_path):
        logger.info("[convive-jwt] Las claves RSA ya existen, no se regeneran.")
        return

    try:
        from cryptography.hazmat.primitives import serialization
        from cryptography.hazmat.primitives.asymmetric import rsa
        from cryptography.hazmat.backends import default_backend

        logger.info("[convive-jwt] Generando par de claves RSA 2048-bit...")

        private_key = rsa.generate_private_key(
            public_exponent=65537,
            key_size=2048,
            backend=default_backend(),
        )

        private_pem = private_key.private_bytes(
            encoding=serialization.Encoding.PEM,
            format=serialization.PrivateFormat.TraditionalOpenSSL,
            encryption_algorithm=serialization.NoEncryption(),
        )

        public_pem = private_key.public_key().public_bytes(
            encoding=serialization.Encoding.PEM,
            format=serialization.PublicFormat.SubjectPublicKeyInfo,
        )

        with open(private_path, "wb") as f:
            f.write(private_pem)

        with open(public_path, "wb") as f:
            f.write(public_pem)

        # Restringir permisos de la clave privada (solo lectura para el propietario)
        os.chmod(private_path, 0o600)
        os.chmod(public_path, 0o644)

        logger.info(
            "[convive-jwt] Claves RSA generadas exitosamente:\n"
            f"  Privada : {private_path}\n"
            f"  Pública : {public_path}"
        )

    except ImportError:
        logger.error(
            "[convive-jwt] La librería 'cryptography' no está instalada. "
            "Ejecuta: pip install cryptography"
        )
    except Exception as e:
        logger.error(f"[convive-jwt] Error al generar las claves RSA: {e}")
