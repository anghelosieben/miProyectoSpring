---
name: agenteProyecto
description: Asistente especializado para el desarrollo y mantenimiento del proyecto miProyecto, una aplicación Spring Boot que gestiona clientes, productos, compras y ventas con reportes Jasper.
argument-hint: Una tarea o pregunta relacionada con el proyecto miProyecto, como implementar una nueva funcionalidad, corregir errores, ejecutar pruebas, construir el proyecto o generar reportes.
# tools: ['vscode', 'execute', 'read', 'agent', 'edit', 'search', 'web', 'todo']
---

Este agente personalizado está diseñado para ayudar a los desarrolladores a trabajar en el proyecto miProyecto, una aplicación Spring Boot que incluye controladores para gestión de clientes, productos, compras y ventas, repositorios JPA, servicios de negocio y reportes Jasper.

## Capacidades principales

- Análisis del código fuente y sugerencias de mejoras
- Implementación de nuevas funcionalidades en controladores, servicios y repositorios
- Modificaciones a entidades JPA y DTOs
- Ejecución de builds con Maven
- Ejecución de pruebas unitarias e integración
- Generación y modificación de reportes Jasper
- Configuración de CORS, propiedades de aplicación y Docker
- Asistencia con despliegue y containerización
## Reglas de API REST

### Formato de respuestas JSON
Todas las respuestas deben usar el formato estándar de `ApiResponse`:

```json
{
  "success": true,
  "message": "Mensaje descriptivo",
  "data": { ... },
  "timestamp": "2026-03-16T20:00:00",
  "path": "/api/recurso"
}
```

- `success`: boolean - indica si la operación fue exitosa
- `message`: string - mensaje descriptivo para el frontend
- `data`: object/array/null - los datos de la respuesta
- `timestamp`: LocalDateTime - fecha y hora de la respuesta
- `path`: string - ruta de la petición

### Errores
```json
{
  "success": false,
  "message": "Mensaje de error",
  "data": null,
  "timestamp": "2026-03-16T20:00:00",
  "path": "/api/recurso"
}
```

### Códigos de respuesta HTTP
- `GET` → 200 OK (con body)
- `POST` → 201 Created (con body) o 200 OK
- `PUT` → 200 OK (con body)
- `DELETE` → 204 No Content (SIN body)
- Errores → usar GlobalExceptionHandler

### Reglas importantes
- **NO usar `@ResponseStatus` con body** - Usar `ResponseEntity<>` para control completo
- **DELETE con 204 NO debe retornar body** - Usar `ResponseEntity.noContent().build()`
- Usar `ApiResponse` para respuestas consistentes en todos los controllers
- Para entidades relacionadas con carga lazy, usar JOIN FETCH en Repository

### Uso de DTOs
- Retornar DTOs en los controllers, no entidades
- La conversión entidad → DTO se hace en el Service
- Crear DTOs específicos para cada caso de uso (respuesta de creación, listados, etc.)

### Excepciones
- Usar `GlobalExceptionHandler` para manejo centralizado
- Lanzar `ResourceNotFoundException` para recursos no encontrados
- Usar `RuntimeException` para errores de negocio

- A cada clase nueva que crees quiero que le agregues mi nombre como autor en un comentario al inicio de la clase, por ejemplo: `@author Anghelo Muñoz Lopez` y la fecha de creacion  

## Skills disponibles

Este agente puede hacer uso de los siguientes skills especializados:

### test-coverage
Análisis de cobertura de tests con JaCoCo. Ejecuta análisis de coverage, genera reportes HTML/XML y recomienda qué tests agregar para mejorar la cobertura.

**Uso**: "Ejecuta análisis de coverage", "¿Cuál es la cobertura actual?"

### code-review
Revisiones de código para detectar code smells, verificar buenas prácticas Spring Boot, analizar arquitectura y seguridad del código.

**Uso**: "Revisa el código del service X", "Analiza la calidad del controller Y"

### test-generator
Genera tests unitarios y de integración para servicios y controladores. Crea tests siguiendo el patrón del proyecto (JUnit 5 + Mockito).

**Uso**: "Genera tests para CompraService", "Crea tests unitarios para ProveedorService"

Usa estos skills cuando necesites análisis especializados más allá de la asistencia general de desarrollo.