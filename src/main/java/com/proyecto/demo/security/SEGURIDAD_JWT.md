# Seguridad JWT - Documentación del Proyecto

## 📋 Resumen

Este proyecto implementa autenticación y autorización usando **JWT (JSON Web Tokens)** con Spring Security.

---

## 🏗️ Arquitectura de Seguridad

```
Usuario → Login/Register → JWT Token → Requests autenticadas
```

---

## 📁 Archivos Creados/Modificados

### Archivos de Seguridad (nuevos)

| Archivo | Descripción |
|---------|-------------|
| `security/config/SecurityConfig.java` | Configuración principal de Spring Security |
| `security/config/GlobalExceptionHandler.java` | Manejo de errores |
| `security/jwt/JwtUtils.java` | Generación y validación de tokens |
| `security/jwt/JwtAuthenticationFilter.java` | Filtro que valida tokens en cada request |
| `security/service/AuthService.java` | Lógica de login y registro |
| `security/service/JpaUserDetailsService.java` | Carga usuario desde base de datos |

### Entidades Modificadas

| Entidad | Cambio |
|---------|--------|
| `model/entity/User.java` | Implementa `UserDetails` de Spring Security |

---

## 🔐 Flujo de Autenticación

### 1. Registro de Usuario
```
POST /api/auth/register
{
  "username": "admin",
  "email": "admin@test.com",
  "password": "password123"
}
```
→ El sistema hashea la contraseña con BCrypt → Guarda en BD → Genera JWT

### 2. Login
```
POST /api/auth/login
{
  "username": "admin",
  "password": "password123"
}
```
→ Valida credenciales → Devuelve JWT token

### 3. Requests Autenticadas
```
GET /api/compras
Authorization: Bearer <JWT_TOKEN>
```
→ El filtro valida el token → Extrae usuario → Configura SecurityContext

---

## ⚙️ Configuración

### application.properties
```properties
# JWT
jwt.secret=clave_secreta_super_segura
jwt.expiration=86400000  # 24 horas en milisegundos
```

### SecurityConfig.java
```java
// Configuraciones principales:
- CSRF: DESHABILITADO (API stateless)
- Sesión: STATELESS (sin estado)
- Rutas públicas: /api/auth/**, /api/productos (GET)
- Rutas protegidas: cualquier otra ruta
```

---

## 🔑 Componentes Clave

### JwtUtils.java
Responsabilidades:
- `generateToken(user)` - Crea un nuevo JWT
- `extractUsername(token)` - Extrae el username del token
- `isTokenValid(token, user)` - Valida que el token sea correcto

### JwtAuthenticationFilter.java
Responsabilidades:
- Intercepta cada request
- Lee el header `Authorization: Bearer <token>`
- Valida el token
- Configura el contexto de seguridad

### JpaUserDetailsService.java
Responsabilidades:
- `loadUserByUsername(username)` - Carga el usuario desde la BD
- Implementa `UserDetailsService` de Spring Security

---

## 🌐 Endpoints

| Método | Endpoint | Descripción | Requiere Token |
|--------|----------|-------------|----------------|
| POST | `/api/auth/register` | Registrar usuario | ❌ No |
| POST | `/api/auth/login` | Login | ❌ No |
| GET | `/api/productos/**` | Listar productos | ❌ No |
| GET/POST/PUT/DELETE | `/api/**` | Demas endpoints | ✅ Sí |

---

## 🔒 Roles y Permisos

El sistema soporta múltiples roles. Por defecto:
- `ROLE_ADMIN` - Acceso total
- `ROLE_USER` - Acceso básico

### Proteger endpoints por rol:
```java
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
public void eliminar(...) { }
```

---

## 📝 Errores Comunes

| Error | Causa | Solución |
|-------|-------|----------|
| 401 Unauthorized | Token inválido o expirado | Login de nuevo |
| 403 Forbidden | Sin permisos | Verificar rol del usuario |
| "Usuario no encontrado" | Username incorrecto | Verificar credentials |
| "Usuario o contraseña incorrectos" | Password incorrecto | Verificar credentials |

---

## 🧪 Pruebas con cURL

### Registrar:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "test", "email": "test@test.com", "password": "123456"}'
```

### Login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "test", "password": "123456"}'
```

### Usar token:
```bash
curl http://localhost:8080/api/productos \
  -H "Authorization: Bearer TU_TOKEN_AQUI"
```

---

## 📌 Notas

- La contraseña se guarda **hasheada** con BCrypt
- El token dura 24 horas por defecto
- El proyecto usa **stateless** (sin sesiones en servidor)
- Compatible con Spring Security 6.x

---

## 👤 Autor

Creado por: Anghelo Muñoz Lopez
Fecha: 2026-02-25
