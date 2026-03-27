# Sistema de Gestión de inventario y ventas
Aplicación web Java EE orientada a la comercialización de productos electrónicos. El sistema permite la gestión completa de inventario, administración de usuarios y procesamiento de ventas con una arquitectura robusta y escalable.

---

## 📌 Descripción

El sistema permite gestionar productos, controlar niveles de stock y registrar ventas mediante una interfaz web interactiva.

Incluye funcionalidades para la administración de clientes, seguimiento de operaciones y generación de información útil para la toma de decisiones en un entorno comercial.

---

## 🚀 Funcionalidades

- Gestión de productos (alta, baja y modificación)
- Control de stock e inventario
- Registro y gestión de ventas
- Administración de clientes
- Gestión de órdenes de compra
- Actualización automática de stock
- Visualización y consulta de datos
- Validación de datos y manejo de errores

---

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java EE**: Servlets y JSP.
- **Arquitectura MVC**: Separación de lógica, datos y vista.
- **Patrón DAO**: Acceso a datos encapsulado.
- **Servidor**: GlassFish v5.1.

### Frontend
- **HTML5, CSS3, JavaScript**.
- **Bootstrap v4.5**: Diseño responsivo.
- **Font Awesome**: Iconografía.

### Base de Datos
- **MariaDB / MySQL**: Almacenamiento relacional.
- **Procedimientos Almacenados**: Optimización de consultas complejas.
- **Connection Pool**: Gestión eficiente de conexiones (Patrón Singleton).

---

## 🧱 Arquitectura

El sistema sigue el patrón **MVC (Modelo - Vista - Controlador)**:

- **Modelo:** clases Java que representan las entidades del sistema
- **Vista:** páginas JSP dinámicas
- **Controlador:** Servlets encargados de la lógica y manejo de solicitudes

Se utiliza el patrón **DAO** para el acceso a datos, favoreciendo la organización y mantenibilidad del código.

---

## 📁 Estructura del proyecto
 -web-app/ # Aplicación Java Web (Código fuente, librerías y recursos)
    ├── src/ # Backend en Java (Servlets, DAO, Modelos)
    ├── web/ # Frontend (Vistas JSP, CSS, JS, Imágenes)
    ├── libs/ # Librerías y dependencias (.jar)
    └── build.xml # Archivo de configuración de Ant para compilación
 -db/ # Scripts SQL (Estructura de la base de datos MariaDB y procedimientos)
 -docs/diagrams/ # Documentación técnica (Diagramas de Clases y DER)
 -docs/images/ # Capturas de pantalla y recursos visuales del sistema

---

## 🔐 Seguridad y autenticación

El sistema incluye funcionalidades básicas de gestión de usuarios, entre ellas la recuperación de contraseñas, permitiendo restablecer el acceso mediante validaciones definidas en el sistema.

## ☁️ Despliegue

La aplicación fue desplegada en un entorno cloud utilizando Jelastic, lo que permitió su acceso remoto sin necesidad de infraestructura local.

Este enfoque facilita la escalabilidad, disponibilidad y pruebas en un entorno similar a producción.
