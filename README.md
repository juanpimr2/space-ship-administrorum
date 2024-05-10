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

¡Listo! Ahora deberías poder ejecutar y acceder a la aplicación de gestión de naves espaciales.
