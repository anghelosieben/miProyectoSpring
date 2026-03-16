# Skill: test-generator

Este skill genera tests unitarios y de integración para los servicios del proyecto miProyecto.

## Capacidades

- **Tests unitarios**: Genera tests con JUnit 5 y Mockito para servicios
- **Tests de integración**: Genera tests de integración para controllers y repositories
- **Patrón de tests**: Sigue el patrón existente del proyecto (comentado en ProductoServiceImplTest.java)
- **Cobertura de métodos**: Crea tests para cada método del servicio (findAll, findById, save, delete)

## Uso

Usa este skill cuando necesites:
- "Genera tests para el servicio X"
- "Crea tests unitarios para el controller Y"
- "usa el skill test-generator para generar tests de [entidad]"
- "Necesito tests de integración para [entidad]"

## Ejemplos de prompts

- "usa el skill test-generator para crear tests para CompraService"
- "genera tests unitarios para ProveedorService"
- "crea tests de integracion para CategoriaController"

## Estructura de tests generados

### Tests unitarios (Service)
- testFindAll()
- testFindByIdFound()
- testFindByIdNotFound()
- testSaveValid()
- testSaveInvalid()
- testDeleteByIdExists()
- testDeleteByIdNotFound()

### Tests de integración (Controller)
- testListarTodos()
- testObtenerPorId()
- testCrear()
- testActualizar()
- testEliminar()

## Notas

- El proyecto usa JUnit 5 y Mockito
- Maven wrapper disponible: `./mvnw`
- Comando para ejecutar tests: `./mvnw test`
- Los tests siguen el patrón de stubs internos (ver ProductoServiceImplTest.java)
- Agregar `@author Anghelo Muñoz Lopez` y `@since 2026-02-25` a cada clase de test creada
