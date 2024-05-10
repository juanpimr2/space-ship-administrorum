# space-ship-administrorum

Un sistema simple de gestión de naves espaciales para el Imperio del Hombre.

## Instrucciones de instalación

Para ejecutar esta aplicación, sigue los pasos a continuación:

### 1. Descargar Docker

Asegúrate de tener Docker instalado en tu sistema. Puedes descargarlo desde [el sitio web oficial de Docker](https://www.docker.com/get-started).

### 2. Clonar el proyecto

Clona este repositorio en tu máquina local utilizando el siguiente comando:

```
git clone https://github.com/juanpimr2/space-ship-administrorum
```

### 3. Construir y ejecutar la aplicación

Navega hasta la carpeta del proyecto clonado y ejecuta los siguientes comandos en tu terminal:

```
cd space-ship-administrorum
docker-compose build
docker-compose up -d
```

### 4. Acceder a la aplicación

Para ver la aplicación, abre tu navegador web y ve a la siguiente URL:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html): Interfaz interactiva para probar los endpoints de la API.

### 5. Acceder a Kafka

Si deseas ver los topics de Kafka, puedes acceder a la siguiente URL:

- [Kafka Control Center](http://localhost:8090/): Control center para visualizar y administrar los topics de Kafka.

### 6. Conectar a la base de datos

Si necesitas conectarte a la base de datos, puedes hacerlo utilizando DBeaver u otro cliente SQL. Utiliza la siguiente información de conexión:

- Host: localhost
- Database: imperialisdb
- Nombre de usuario: adeptusCustode
- Contraseña: praiseTheEmperor

### 7. Montar la aplicación en Eclipse

Para montar la aplicación en Eclipse y ejecutar los tests, sigue estos pasos:

1. Abre Eclipse en tu máquina.

2. Importa el proyecto Maven existente seleccionando `File` -> `Import` -> `Existing Maven Projects`.

3. Selecciona la carpeta del proyecto clonado de la aplicación.

4. Ejecuta el comando `mvn clean install` para instalar las dependencias de Maven y construir el proyecto.

### 8. Ejecutar los tests unitarios y de integración

Una vez que el proyecto esté importado en Eclipse, puedes ejecutar los tests unitarios y de integración siguiendo estos pasos:

1. Navega hasta las clases de tests JUnit en el paquete adecuado de tu proyecto.

2. Haz clic derecho en la clase de tests que deseas ejecutar.

3. Selecciona `Run As` -> `JUnit Test` para ejecutar los tests unitarios.

4. Para ejecutar los tests de integración, asegúrate de que tus configuraciones de prueba estén correctamente definidas en las clases de tests, y luego ejecútalos de la misma manera que los tests unitarios.

¡Listo! Ahora deberías poder ejecutar y acceder a la aplicación de gestión de naves espaciales.
