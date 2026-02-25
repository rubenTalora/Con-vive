# Configuración de Seguridad JWT - Spring Boot

## 🔒 Cambios Implementados

Se ha agregado **autenticación JWT** a todos los endpoints de la API Spring Boot. Ahora **TODOS los endpoints requieren un token JWT válido** excepto los endpoints de Swagger.

## 📋 Endpoints Protegidos

Todos los endpoints bajo `/api/**` ahora requieren autenticación:
- ❌ `POST /api/chats/send` - Requiere token
- ❌ `GET /api/chats/` - Requiere token
- ❌ `GET /api/chats/{id}/messages` - Requiere token
- ✅ `GET /swagger-ui/**` - Público (sin token)

## 🔑 Cómo Usar

### 1. Obtener un Token JWT

Necesitas obtener un token JWT válido desde tu backend de Odoo (convive-jwt).

**Ejemplo de petición a Odoo:**
```bash
POST http://localhost:8069/api/auth/login
Content-Type: application/json

{
  "login": "usuario@example.com",
  "password": "password123"
}
```

**Respuesta esperada:**
```json
{
  "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user_id": 123,
  "username": "usuario@example.com"
}
```

### 2. Usar el Token en Peticiones a Spring Boot

Incluye el token en el header `Authorization` con el prefijo `Bearer`:

**Ejemplo con curl:**
```bash
curl -X POST http://localhost:8080/api/chats/send \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -H "Content-Type: application/json" \
  -d '{
    "chatId": 1,
    "userId": 123,
    "message": "Hola mundo"
  }'
```

**Ejemplo con JavaScript/Fetch:**
```javascript
fetch('http://localhost:8080/api/chats/send', {
  method: 'POST',
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    chatId: 1,
    userId: 123,
    message: 'Hola mundo'
  })
});
```

**Ejemplo con Postman:**
1. Ve a la pestaña **Authorization**
2. Selecciona **Bearer Token**
3. Pega tu token JWT en el campo

### 3. Sin Token - Error 401/403

Si intentas acceder sin token o con un token inválido:

```bash
# Sin token
curl -X POST http://localhost:8080/api/chats/send
# ❌ Respuesta: 401 Unauthorized o 403 Forbidden
```

## 🔧 Configuración

### Clave Secreta JWT

La clave secreta está definida en `src/main/resources/application.yml`:

```yaml
jwt:
  secret: 404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
```

**IMPORTANTE:** Esta clave debe ser la **MISMA** que usa tu backend de Odoo para firmar los tokens. Si Odoo usa una clave diferente, actualiza este valor.

### Verificar la Clave de Odoo

Busca en tu módulo `convive-jwt` el archivo donde se define la clave secreta, probablemente en:
- `odoo/extra-addons/convive-jwt/services/jwt_service.py`
- `odoo/keys/private_key.pem` (si usa RSA)

## 🧪 Testing

### Probar que la Seguridad Funciona

**1. Sin token (debe fallar):**
```bash
curl -X POST http://localhost:8080/api/chats/send \
  -H "Content-Type: application/json" \
  -d '{"chatId": 1, "userId": 123, "message": "Test"}'
```
**Resultado esperado:** 401 Unauthorized o 403 Forbidden

**2. Con token válido (debe funcionar):**
```bash
curl -X POST http://localhost:8080/api/chats/send \
  -H "Authorization: Bearer TU_TOKEN_AQUI" \
  -H "Content-Type: application/json" \
  -d '{"chatId": 1, "userId": 123, "message": "Test"}'
```
**Resultado esperado:** 200 OK con el mensaje guardado

## 📝 Archivos Creados/Modificados

```
Springboot/
├── pom.xml (agregadas dependencias de Spring Security y JWT)
├── src/main/resources/application.yml (agregada configuración jwt.secret)
└── src/main/java/com/convive/
    ├── config/
    │   ├── SecurityConfig.java (configuración de seguridad)
    │   ├── JwtAuthenticationFilter.java (filtro JWT)
    │   └── CustomUserDetailsService.java (servicio de usuarios)
    └── service/
        └── JwtService.java (servicio para validar tokens)
```

## ⚠️ Próximos Pasos

1. **Sincronizar claves:** Asegúrate de que Spring Boot y Odoo usen la **misma clave secreta** JWT
2. **Implementar UserDetailsService real:** Actualmente acepta cualquier usuario. Deberías conectarlo a tu base de datos de usuarios
3. **Agregar endpoint de login:** Si quieres que Spring Boot también pueda generar tokens (opcional)
4. **Manejo de errores:** Personalizar las respuestas de error 401/403

## 🐛 Solución de Problemas

**Problema:** "Token inválido" con un token que debería ser válido

**Solución:** Verifica que:
- La clave secreta en `application.yml` sea la misma que usa Odoo
- El token no haya expirado
- El formato del header sea exactamente: `Authorization: Bearer {token}`

**Problema:** 403 Forbidden en todos los endpoints

**Solución:** Reinicia la aplicación y verifica los logs de Spring Boot para ver detalles del error
