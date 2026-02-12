# Especificación Preliminar de las APIs


## 1. API de Autenticación y Usuarios (Odoo)


| Método | Endpoint | Descripción | Requisito de Token |
| :--- | :--- | :--- | :--- |
| **POST** | `/register` | Registra un nuevo usuario en Odoo. | No |
| **POST** | `/login` | Autentica al usuario y retorna un JWT. | No |
| **GET** | `/user` | Obtiene los datos del perfil del usuario actual. | Sí |
| **PUT** | `/user/update` | Modifica datos del perfil | Sí |

---

## 2. API de Suscripciones y Pagos (Odoo)


| Método | Endpoint | Descripción | Requisito de Token |
| :--- | :--- | :--- | :--- |
| **GET** | `/plans` | Lista los tipos de suscripciones disponibles y precios. | No |
| **GET** | `/status` | Verifica el estado de suscripción del usuario en Odoo. | Sí |
| **POST** | `/checkout` | Inicia el proceso de pago para un plan específico. | Sí |
| **GET** | `/invoices` | Recupera el historial de facturas en PDF desde Odoo. | Sí |

---

## 3. API de Publicaciones de pisos


| Método | Endpoint | Descripción | Requisito de Token |
| :--- | :--- | :--- | :--- |
| **GET** | `/` | Lista todas las publicaciones  | Sí |
| **GET** | `/:id` | Obtiene el detalle de una publicación | Sí |
| **POST** | `/create` | Crea una nueva publicación (verifica suscripción activa y si no manda a odoo) | Sí |
| **PUT** | `/:id` | Edita una publicación existente (solo si es propietario) | Sí |
| **DELETE** | `/:id` | Elimina una publicación (dueño o admin) | Sí |

---

## 4. API de Chats


| Método | Endpoint | Descripción | Requisito de Token |
| :--- | :--- | :--- | :--- |
| **GET** | `/` | Lista todas las conversaciones del usuario. | Sí |
| **GET** | `/:id/messages` | Obtiene los mensajes de un chat específico. | Sí |
| **POST** | `/send` | Envía un mensaje a otro usuario. | Sí |

---



## 5. API de Administrador

| Método | Endpoint | Descripción | Requisito de Token |
| :--- | :--- | :--- | :--- |
| **PATCH** | `/users/:id/ban` | Bloquea el acceso a un usuario | Sí (Admin) |
| **DELETE** | `/:ID` | Borra publicaciones no deseadas en la app | Sí (Admin) |

---