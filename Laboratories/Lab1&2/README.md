<!--
PROJECT NAME
-->

# Laboratorios 1 and 2
<a id="readme-top"></a>

<!--
PROJECT DESCRIPTION
-->
## 📜 Descripción

En este archivo se encuentra ubicado el laboratorio 1 y 2, en la cual recreamos algunos de los primeros cifrados utilizados, y a su vez, se implementaron algoritmos de fuerza bruta para poder romper el cifrado de dichos algoritmos.

## 📖 Repositorio
* https://github.com/estebandonis/Data_Encryption.git


## ✨ Contenido
- Laboratorio 1
  - Funciones de transformación
    - Binario
    - Base64
    - ASCII
  - Algoritmos de cifrado:
    - Cifrado César
    - Cifrado Affine
    - Cifrado Vigenère
  - Análisis de frecuencia de textos cifrados
  - Ataque de fuerza bruta


- Laboratorio 2
  - Traducción de cadenas en los siguientes formatos:
    - ASCII
    - Binario
    - Base64
  - Descifrado de imágenes cifradas
  - Creación de imagen por medio de la aplicación de operación XOR a dos imágenes 

## 🚀 Instalación y Ejecución

1. Clona este repositorio e instala las dependencias:

    ```bash
    git clone https://github.com/estebandonis/Data_Encryption.git
    cd Laboratories/Lab1&2
    ```

2. Luego compila los archivos Java: 

    ```bash
    javac -d out src/main/java/one/*.java
    ```

3. Para ejecutar los archivos compilados, ejecuta el siguiente comando:

    ```bash
    java -cp out one.MainApplication
    ```

## 📂 Estructura del Proyecto

<details>
  <summary>Descripción de Carpetas</summary>

La estructura del proyecto está organizada de la siguiente manera:

- **public/**: Archivos estáticos y recursos accesibles públicamente.
- **src/**
  - **components/**: Componentes reutilizables de la interfaz de usuario.
  - **pages/**: Rutas de la aplicación, organizadas en carpetas según su funcionalidad.
    - **api/**: Endpoints de la API.
    - **404/**: Página de error 404.
    - **home/**: Página de inicio de la aplicación.
    - **login/**: Página de inicio de sesión.
  - **services/**: Servicios auxiliares, incluyendo el manejo de correos y configuraciones.
    - **email/**: Servicios para envío de correos electrónicos.
    - **namesEnums.js, reportTypes.js, world.js**: Archivos de configuración y utilidades.
  - **styles/**: Archivos CSS para los estilos de la aplicación.
  
</details>
<p align="right">(<a href="#readme-top">Ir al inicio</a>)</p>
 -->

## 📦 Dependencias Principales

Las principales dependencias del proyecto incluyen:
* [![Java][Java]][Java-url]
* [![JavaFX][JavaFX]][JavaFX-url]
<p align="right">(<a href="#readme-top">Ir al inicio</a>)</p>

<!-- ## 🛠️ API Endpoints
<details>
  <summary>Principales Endpoints</summary>
  
  La API está construida utilizando Next.js y organiza sus endpoints en función de las entidades principales del sistema.
  A continuación se presentan algunos de los endpoints más importantes:

- **api/auth/**: Manejo de autenticación y autorización de usuarios.
- **api/estadisticas/**: Endpoints para obtener estadísticas detalladas de exploradores e instituciones.
- **api/reports/**: Endpoints para generar reportes personalizados en formato Excel.

Cada endpoint está diseñado para recibir y responder con datos JSON, permitiendo la integración con los módulos del sistema.

</details>
<p align="right">(<a href="#readme-top">Ir al inicio</a>)</p> -->

<!-- MARKDOWN LINKS & IMAGES -->
[Java]: https://img.shields.io/badge/Java-3776AB?style=flat&logo=java&logoColor=white
[Java-url]: https://www.java.com/
[JavaFX]: https://img.shields.io/badge/javafx-er2?style=flat&logo=javafx&color=orange
[JavaFX-url]: https://openjfx.io/
[Website]: https://img.shields.io/website?url=https://lc2tech.com/
[Website-url]: https://estebandonis.netlify.app/
[Linkedin-est]: https://www.linkedin.com/in/esteban-donis-384819204/
[Linkedin]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[Github-est]: https://github.com/estebandonis
[GitHub]: https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white