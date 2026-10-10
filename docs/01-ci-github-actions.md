# Paso 1 — CI con GitHub Actions

> Nota de defensa para entrevista. Qué monté, por qué, y cómo explicarlo.

## Qué es
**Integración Continua (CI):** cada vez que subo código (push a `master`) o abro un Pull Request, un servidor de GitHub (un *runner*) compila el proyecto y ejecuta los tests automáticamente. Si algo falla, lo veo al instante y el PR queda marcado en rojo.

Fichero: `.github/workflows/ci.yml`.

## Qué hace el workflow (paso a paso)
1. **Trigger:** `push` a `master` y `pull_request` contra `master`.
2. **`actions/checkout@v4`:** descarga el código en el runner.
3. **`actions/setup-java@v4`:** instala **JDK 21 (Temurin)** y activa **caché de Maven** (`~/.m2`) para no volver a descargar dependencias en cada ejecución → builds más rápidos.
4. **`./mvnw -B -ntp verify`:** usa el **Maven Wrapper** (misma versión de Maven para todos) y ejecuta el ciclo hasta `verify`: compila, corre los tests (surefire) y empaqueta el jar.
   - `-B` = modo batch (no interactivo), `-ntp` = sin logs de descarga (output limpio).
5. **`upload-artifact`:** guarda los informes de test (`target/surefire-reports`) aunque el build falle (`if: always()`), para poder revisarlos.

## Por qué así (decisiones)
- **JDK 21 Temurin:** el proyecto declara `java.version=21` en el `pom.xml`; uso la misma versión en CI para reproducir el entorno.
- **Maven Wrapper (`mvnw`) en vez de un Maven del sistema:** garantiza **builds reproducibles** (misma versión de Maven en local y en CI).
- **`verify` en vez de solo `test`:** además de testear, **empaqueta** el jar, demostrando que la app realmente se construye.
- **Tests unitarios puros:** los tests usan Mockito y no cargan el contexto de Spring, así que **no hacen falta Postgres ni Kafka** en CI → pipeline rápido y estable.
- **`permissions: contents: read`:** principio de mínimo privilegio; este job solo necesita leer el repo.

## Cómo lo defiendo (posibles preguntas)
- *¿Qué pasa si un test falla en un PR?* → El check sale en rojo y bloquea el merge (si activo la branch protection). Feedback temprano, menos bugs a `master`.
- *¿Por qué cachear Maven?* → Evita descargar dependencias en cada run; reduce el tiempo de pipeline.
- *¿Siguiente paso?* → Construir y publicar la **imagen Docker** en un registry (GHCR) desde el propio pipeline (CD) — ver paso 2.

## Posible mejora futura
- Añadir **branch protection** en `master` exigiendo que CI pase antes de mergear.
- Añadir análisis de calidad (SonarCloud) y cobertura.
