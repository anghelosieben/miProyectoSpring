# Skill: test-coverage

Este skill proporciona capacidades para analizar y mejorar la cobertura de tests en el proyecto miProyecto.

## Capacidades

- **Análisis de cobertura**: Ejecutar análisis de cobertura de código con JaCoCo
- **Reportes de cobertura**: Generar reportes HTML/XML de cobertura de tests
- **Evaluación de calidad**: Identificar áreas del código con baja cobertura
- **Recomendaciones**: Sugerir qué tests agregar para mejorar la cobertura
- **Métricas**: Interpretar estadísticas de coverage (líneas, ramas, métodos, clases)

## Uso

Usa este skill cuando necesites:
- Conocer la cobertura de tests actual del proyecto
- Mejorar la cobertura de código
- Ejecutar análisis de coverage con Maven
- Revisar qué partes del código no están probadas

## Ejemplos de tareas

- "Ejecuta análisis de cobertura"
- "¿Cuál es la cobertura actual del proyecto?"
- "¿Qué métodos no tienen tests?"
- "Genera reporte de coverage"

## Notas

- El proyecto usa Spring Boot con JUnit y Mockito
- Maven wrapper disponible: `./mvnw`
- Comando para coverage: `./mvnw test` (genera reportes en `target/site/jacoco`)
