# mi-proyecto

Proyecto demo Spring Boot con PostgreSQL.
Este proyecto esta configurado para correr en Docker
- Se inicio creando usuario, roles y permisos, para practicar Spring Security, Spring Boot Rest.
- Se puede continuar como servicio de almacenes o ventas y compras etc.

## Requisitos

- **Java:** 21
- **Spring Boot:** 4.0.2
- **PostgreSQL:** 15+ recomendado
- **Docker:** 20+ recomendado
- **Maven:** 3.9+

## Iniciar en equipo local

1. Instala Java 21 y Maven.
2. Configura PostgreSQL y crea una base de datos (ejemplo: `mi_proyecto_db`).
3. Ajusta las credenciales en `src/main/resources/application.properties` o `application.yml`.
4. Ejecuta:

   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## Iniciar con Docker

1. Asegúrate de tener Docker y Docker Compose instalados.
2. Revisa y ajusta los archivos `Dockerfile` y `docker-compose.yml` si es necesario.
3. Ejecuta:

   ```bash
   docker-compose up --build
   ```

   Esto levantará la app y la base de datos PostgreSQL en contenedores.

## Comandos útiles Docker

- Levantar en segundo plano:
  ```bash
  docker-compose up -d
  ```
- Ver logs:
  ```bash
  docker-compose logs -f
  ```
- Parar y eliminar contenedores:
  ```bash
  docker-compose down
  ```

## Notas

- El proyecto usa Lombok, asegúrate de tener el plugin en tu IDE.
- El perfil de test usa `application-test.properties`, no existe este archivo , las configuarciones las trae de `application.properties`
- Para pruebas de integración, asegúrate que la base de datos esté accesible.


- Despues de iniciar el proyecto en el navegador probar las rutas:

http://localhost:8080/
http://localhost:8080/hola
http://localhost:8080/api/productos
http://localhost:8080/api/productos/1

---


