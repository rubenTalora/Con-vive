# Requerimientos funcionales y no funcionales del sistema - App Inmobiliaria

## Backend

### Catálogo de pisos y Búsqueda de compañeros

- **Funcionales**
    ```text
    RF01 - El usuario puede consultar las publicaciones de pisos y compañeros del catálogo
    RF02 - El usuario puede filtrar la búsqueda por localidad, precio, metro cuadrado...
    RF03 - El usuario podrá guardar publicaciones en una lista de Favoritos
    RF04 - El usuario puede crear, editar y eliminar sus propias publicaciones
    RF05 - El sistema permite varias imágenes para cada piso
    RF06 - El sistema mostrará la información del piso y del anunciante
    RF07 - El sistema debe poder validar el token de sesión creado por Odoo
    RF08 - El catálogo mostrará filtros como: "ciudad", "venta", "núm habitaciones"
    ```

- **No Funcionales**
    ```text
    RNF01 - El servidor retornará los datos en formato JSON
    RNF02 - El servidor mostrará 10 publicaciones por página (paginación)
    ```

### Chats

- **Funcionales**
    ```text
    RF01 - El usuario puede solicitar abrir un chat privado con el anunciante de una publicación
    RF02 - El sistema permite el envío de mensajes únicamente de texto.
    RF03 - El admin puede enviar avisos y notificaciones de seguridad directamente al chat
    ```

- **No Funcionales**
    ```text
    RNF01 - La latencia en el envío de mensajes debe ser inferior a 500ms
    RNF02 - El historial de mensajes debe ser cifrado
    ```

### Odoo

- **Funcionales**
    ```text
    RF01 - El usuario debe ser capaz de crear una cuenta desde la web
    RF02 - El usuario debe insertar un método de pago para gestionar suscripciones
    RF03 - El sistema comprobará las credenciales y las subscripciones del usuario
    RF04 - El sistema controlará el límite de publicaciones según el plan contratado en Odoo
    RF05 - El usuario debe poder modificar o cancelar su suscripción activa
    RF06 - El usuario debe poder modificar los parámetros de su perfil
    ```

- **No Funcionales**
    ```text
    RNF01 - El sistema retorna un token JWT tras la verificación del usuario en Odoo
    ```

## Frontend

### App Móvil

- **Funcionales**
    ```text
    RF01 - El usuario debe iniciar sesión con correo y contraseña vinculados a Odoo
    RF02 - El sistema debe guardar el historial de búsquedas recientes del usuario
    RF03 - El usuario debe poder subir fotos directamente desde la galería
    RF04 - El usuario debe poder filtrar la búsqueda mediante su ubicación seleccionada
    RF05 - El usuario debe poder gestionar su perfil, editar; crear o borrar sus publicaciones y visualizar sus pisos guardados
    ```

- **No Funcionales**
    ```text
    RNF01 - La interfaz debe ser intuitiva
    RNF02 - El tiempo de carga de la pantalla principal no debe superar los 3 segundos
    ```

### Aplicación admin (que es la misma con diferentes interacciones)

- **Funcionales**
    ```text
    RF01 - El administrador debe poder eliminar publicaciones de los usuarios
    RF02 - El administrador debe poder gestionar las metadatos de las localidades y zonas
    RF03 - El administrador debe poder bloquear usuarios
    ```

- **No Funcionales**
    ```text
    RNF01 - Acceso restringido mediante roles de administrador gestionados desde Odoo
    ```