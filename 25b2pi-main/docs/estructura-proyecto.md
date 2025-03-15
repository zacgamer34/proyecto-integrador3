# Estructura del Proyecto

## Visión General
Este documento detalla la estructura completa del proyecto, describiendo la organización de directorios y archivos principales.

## Estructura de Directorios

### Directorio Raíz
```
.
├── .mvn/                  # Configuración de Maven Wrapper
├── docs/                  # Documentación del proyecto
├── src/                   # Código fuente
├── target/                # Archivos compilados y build
├── .dockerignore          # Archivos ignorados por Docker
├── .env                   # Variables de entorno
├── .gitattributes         # Configuración de atributos Git
├── .gitignore             # Archivos ignorados por Git
├── Dockerfile             # Configuración de Docker
├── mvnw                   # Maven Wrapper (Unix)
├── mvnw.cmd               # Maven Wrapper (Windows)
├── pom.xml                # Configuración de Maven
├── project.toml           # Configuración del proyecto
└── README.md              # Documentación principal
```

### Directorio src/
```
src/
├── main/
│   ├── java/             # Código fuente Java
│   └── resources/         # Recursos y configuraciones
└── test/
    └── java/             # Pruebas unitarias
```

### Directorio docs/
```
docs/
├── entidades/            # Documentación de entidades│   
│   └── User.md           # Entidad Usuario
├── docker-configuracion.md    # Configuración de Docker
├── estructura-proyecto.md     # Este documento
└── Exportar json OpenAPI.md   # Documentación API
```

## Estructura de Código Fuente

### Paquetes Principales (src/main/java/com/cesde/proyecto_integrador/)
```
com.cesde.proyecto_integrador/
├── auth/                  # Autenticación y autorización
├── config/               # Configuraciones de la aplicación
├── controller/           # Controladores REST
├── dto/                  # Objetos de transferencia de datos
├── exception/            # Manejo de excepciones
├── model/                # Entidades y modelos
├── repository/           # Interfaces de repositorio
├── service/              # Lógica de negocio
└── ProyectoIntegradorApplication.java  # Punto de entrada
```

### Descripción de Paquetes

#### auth/
- Implementación de autenticación y autorización
- Configuración de seguridad
- Filtros de seguridad

#### config/
- Configuraciones de Spring Boot
- Configuración de bases de datos
- Configuración de servicios externos

#### controller/
- Endpoints REST
- Manejo de peticiones HTTP
- Validación de entrada

#### dto/
- Objetos para transferencia de datos
- Mapeo entre entidades y DTOs

#### exception/
- Excepciones personalizadas
- Manejadores globales de excepciones

#### model/
- Entidades JPA
- Modelos de dominio

#### repository/
- Interfaces de acceso a datos
- Consultas personalizadas

#### service/
- Implementación de lógica de negocio
- Transacciones
- Validaciones

### Recursos (src/main/resources/)
```
resources/
├── META-INF/             # Metadatos
└── application.properties # Configuración principal
```

### Pruebas (src/test/java/)
- Organización espejo de los paquetes principales
- Pruebas unitarias por componente
- Pruebas de integración

## Archivos de Configuración

### Maven (pom.xml)
- Dependencias del proyecto
- Plugins y configuración de build
- Propiedades del proyecto

### Docker
- **Dockerfile**: Configuración para contenedorización
- **.dockerignore**: Exclusiones para la imagen Docker

### Entorno
- **.env**: Variables de entorno para desarrollo
- **application.properties**: Configuración de la aplicación

## Directorios Generados

### target/
```
target/
├── classes/              # Bytecode compilado
├── generated-sources/    # Código generado
├── maven-archiver/       # Archivos de configuración
├── surefire-reports/     # Reportes de pruebas
└── *.jar                 # Artefactos generados
```