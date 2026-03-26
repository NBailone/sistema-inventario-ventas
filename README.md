# Sistema de Gestión de inventario y ventas
Aplicación web orientada a la gestión de inventario, productos, clientes y operaciones de venta. El sistema permite controlar el stock, registrar transacciones y administrar información comercial de manera eficiente.

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

## 🛠️ Tecnologías utilizadas

### Backend
- Java (Servlets, JSP)
- Arquitectura MVC
- Patrón DAO (Data Access Object)

### Frontend
- HTML5, CSS3, JavaScript
- Bootstrap

### Base de datos
- MySQL / MariaDB
- Diseño relacional

---

## 🧱 Arquitectura

El sistema sigue el patrón **MVC (Modelo - Vista - Controlador)**:

- **Modelo:** clases Java que representan las entidades del sistema
- **Vista:** páginas JSP dinámicas
- **Controlador:** Servlets encargados de la lógica y manejo de solicitudes

Se utiliza el patrón **DAO** para el acceso a datos, favoreciendo la organización y mantenibilidad del código.

---

## 📁 Estructura del proyecto
- src/ # Código backend en Java
- web/ # Vistas JSP y recursos frontend
- database/ # Scripts SQL (estructura de base de datos)

---

## 🔌 Conexión a base de datos

La conexión a la base de datos se implementa mediante el patrón de diseño **Singleton**, a través de una clase `ConnectionPool`.

Esta clase se encarga de:
- Cargar el driver de la base de datos
- Establecer la conexión con la base de datos
- Administrar un pool de conexiones
- Proveer instancias de tipo `Connection` de manera eficiente

Este enfoque permite optimizar el uso de recursos y mejorar el rendimiento de la aplicación.


## 🔐 Seguridad y autenticación

El sistema incluye funcionalidades básicas de gestión de usuarios, entre ellas la recuperación de contraseñas, permitiendo restablecer el acceso mediante validaciones definidas en el sistema.

## ☁️ Despliegue

La aplicación fue desplegada en un entorno cloud utilizando Jelastic, lo que permitió su acceso remoto sin necesidad de infraestructura local.

Este enfoque facilita la escalabilidad, disponibilidad y pruebas en un entorno similar a producción.
