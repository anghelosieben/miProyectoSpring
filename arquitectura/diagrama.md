# Diagrama de arquitectura

```mermaid
flowchart LR
  subgraph Users
    A[Browser (Angular) / Client]
  end

  subgraph DockerCompose[Docker Compose]
    direction TB
    BackendContainer["Backend container\n(mi-proyecto-backend)"]
    DbContainer["PostgreSQL container\n(mi-proyecto-postgres)"]
  end

  subgraph BackendApp[Spring Boot Application]
    direction LR
    AppMain["MiProyectoApplication\n@SpringBootApplication"]
    Controllers["Controllers (REST)\n(e.g., HolaController)"]
    Services["Services / Business Logic"]
    Repositories["Repositories (Spring Data JPA)"]
    Entities["Domain Entities / DTOs"]
    Jasper["JasperReports (reportes)"]
  end

  A -->|HTTP (JSON)| BackendContainer
  BackendContainer -->|runs jar (app.jar)| BackendApp
  BackendContainer -->|connects via JDBC| DbContainer

  AppMain --> Controllers
  Controllers --> Services
  Services --> Repositories
  Repositories --> Entities
  Repositories -->|SQL| DbContainer
  Services --> Jasper

  subgraph Build[Build / Dockerfile]
    Builder["Maven builder stage\n(mvn clean package)"]
    Runtime["Runtime stage\n(eclipse-temurin:21-jre)\napp.jar -> container"]
    Builder -->|produces| Runtime
    Runtime --> BackendContainer
  end

  DbContainer -.->|volume: postgres-data| PostgresVolume["Persistent Volume"]
```
