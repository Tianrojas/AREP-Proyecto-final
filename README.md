# Proyecto final - Reducción del desperdicio de alimentos

### Descripción
Este proyecto consiste en un prototipo desarrollado para implementar funciones de AWS Lambda junto con el servicio de autenticación Cognito. Utiliza MongoDB como base de datos y está escrito principalmente en Node.js y Java.

### Arquitectura del Prototipo
![Untitled](https://github.com/Tianrojas/AREP-Proyecto-final/assets/62759668/31be3e16-199d-47c7-8964-9d5ad7cc731c)
El prototipo sigue una arquitectura basada en microservicios, donde las funciones de Lambda actúan como unidades independientes de funcionalidad. Cognito se encarga de la autenticación y autorización de usuarios, mientras que MongoDB proporciona la capa de persistencia de datos.

### Estructura del Repositorio
El repositorio está estructurado de la siguiente manera:
- **lambdaCognito**: Contiene el código relacionado con las funciones de Lambda y la configuración de Cognito.
- **lambdaTest**: Incluye el código de prueba y la configuración para las pruebas unitarias.
  - **src**: Contiene el código fuente de la aplicación.
  - **target**: Almacena los archivos compilados y generados durante el proceso de compilación.

### Componentes Principales
Los componentes principales del proyecto son:
- **AWS Lambda**: Utilizado para ejecutar código sin necesidad de aprovisionar o administrar servidores.
- **Amazon Cognito**: Proporciona la autenticación, autorización y gestión de usuarios.
- **MongoDB**: Base de datos NoSQL utilizada para almacenar y recuperar datos de manera eficiente.
- **Node.js y Java**: Lenguajes de programación principales utilizados para implementar las funciones de Lambda y la lógica de la aplicación.

### Ejemplo de uso

![Video guiado](https://github.com/Tianrojas/AREP-Proyecto-final/blob/main/hi.mp4) 

### Flujo de Trabajo
El flujo de trabajo típico del proyecto implica la interacción de los usuarios a través de la interfaz de usuario, la autenticación a través de Cognito, la ejecución de funciones de Lambda para acceder o manipular datos en MongoDB y la respuesta al usuario final.

Para contribuir al proyecto, se siguen las prácticas estándar de control de versiones utilizando Git y se realizan pruebas unitarias para garantizar la calidad del código antes de la implementación.

Este README proporciona una visión general del proyecto y su estructura, lo que facilita la comprensión y la colaboración entre los miembros del equipo.
