
# BCNC Pricing API

API REST construida como solución a la prueba técnica de BCNC Group. Permite consultar el precio aplicable de un producto ZARA en una fecha y hora determinada, considerando reglas de prioridad de tarifas.

> 💡 Desarrollado con enfoque profesional: Java 17, Spring Boot 3.5, arquitectura hexagonal, principios SOLID, validaciones HTTP, cobertura de tests, documentación Swagger y buenas prácticas de código limpio.

---

## ⚙️ Tecnologías utilizadas y justificación

| Tecnología                      | Justificación                                                                 |
|-------------------------------|------------------------------------------------------------------------------|
| Java 17                        | Versión moderna, estable y compatible con el ecosistema Spring.             |
| Spring Boot 3.5.0              | Framework robusto para construir APIs REST de forma rápida y segura.       |
| Spring Data JPA                | Acceso a datos declarativo usando entidades y repositorios.                 |
| H2 Database (in-memory)        | Base de datos embebida ideal para testing y validación de lógica.           |
| Lombok                         | Reduce el código boilerplate (constructores, getters, etc.).                |
| Checkstyle + custom rules      | Asegura estilo de código uniforme y legible.                                |
| JaCoCo                         | Generación de reporte de cobertura de tests.                                |
| Springdoc OpenAPI 3.0          | Documentación interactiva vía Swagger UI.                                   |
| JUnit 5 + Mockito              | Frameworks modernos y populares para testing unitario y de integración.     |
| Gradle                         | Sistema de construcción flexible y rápido para gestionar dependencias.      |

---

## 🧠 Justificación de decisiones técnicas

- **Arquitectura hexagonal**: permite separar completamente el dominio de la infraestructura. Esto facilita pruebas, extensibilidad y mantenibilidad.
- **DTOs y mappers explícitos**: desacoplan el modelo interno del contrato externo, facilitando el control del API.
- **Validaciones y manejo de errores personalizados**: se gestionan errores 404, 500 con cuerpos claros y trazables.
- **Configuración de seguridad con headers estándar**: previene vulnerabilidades comunes (CSP, Referrer, Permissions Policy).
- **Cobertura de tests y reportes**: asegura robustez del servicio y permite validar las reglas de negocio propuestas.
- **Documentación OpenAPI**: define contrato claro, con ejemplos de request/response y posibles errores.

---

## 🧱 Explicación de la arquitectura

El proyecto está dividido en 3 grandes capas siguiendo **arquitectura hexagonal**:
```
📦 com.bcnc.princing.demo
├── 🔁 application → Casos de uso de negocio
│ └── service
│ └── serviceImpl
├── 🧠 domain → Modelo del dominio puro (Price)
│       └── model → record Price
│       └── port → Interfaz PriceRepository (puerto)
├── 🌐 infrastructure → Adapters externos
│ ├── controller → REST controller (entrada)
│ │     └── dto → PriceResponse DTO
│ ├── adapter → Implementación de puerto con JPA
│ │     └── repository → DatasRepository de las entidadess
│ ├── entity → Entidades JPA (PriceEntity, etc.)
│ └── mapper → Entidades mappers
├── ⚙️ config → Manejo de errores, seguridad, propiedades
└── 🚀 DemoPricingApplication.java
```

> Este diseño desacopla completamente la lógica de negocio del acceso a datos y los detalles técnicos de entrega.

---

## 🧪 Tests y cobertura

Se implementaron tests en cada capa del sistema:

- `PriceServiceImpl` con reglas de negocio.
- `PriceRepositoryAdapter` validando mapeo y repositorio.
- `SpringDataPriceRepository` usando `@DataJpaTest`.
- `PriceController` con `@WebMvcTest` y MockMvc.
- **5 pruebas funcionales obligatorias** con fechas del enunciado.

Cobertura verificada con JaCoCo y reportada en HTML (`build/reports/jacoco/test/html/index.html`).

---

## 📄 Documentación OpenAPI

El contrato está definido en `openapi.yaml` compatible con Swagger UI.

- Parámetros y headers descritos
- Ejemplos de respuestas `200`, `404`, `500`
- Esquema `PriceResponse`

---

## ▶️ Ejecución local

1. Clonar el proyecto:
```bash
  git clone https://github.com/tu_usuario/bcnc-pricing-api.git
  cd bcnc-pricing-api
```

2. Ejecutar aplicación:
```bash
  ./gradlew bootRun
```

## ⚙️ Endpoints disponibles

### `GET /api/v1/prices`

- http://localhost:8080/api/v1/prices?date=2020-06-14T12:00:00&productId=35455&brandId=1

Consulta precio aplicable

#### Parámetros
| Nombre         | Tipo    | Descripción                     |
|----------------|---------|---------------------------------|
| `date`         | `datetime` | Fecha y hora de consulta       |
| `productId`    | `long`     | ID del producto                |
| `brandId`      | `long`     | ID de la marca (ZARA = 1)      |

#### Headers
- `X-Request-ID` (requerido) - abc123
- `X-Correlation-ID` (requerido) - trace-001

## 📌 Notas técnicas
Las respuestas de error siguen el estándar:

```
{
    "message": "No applicable price found.",
    "timestamp": "2025-05-23T22:18:42.9828077-05:00",
    "error": "NOT_FOUND"
}
```

---

## 🧪 Ejecutar pruebas

```bash
    ./gradlew test
    ./gradlew jacocoTestReport
```

---

## 🔧 Configuración de estilo y calidad

- `checkstyle.xml`: validaciones de estilo (nombres, indentación, braces, imports)
- `jacocoTestReport`: cobertura generada al correr los tests
- `build.gradle`: incluye configuración de OpenAPI, Checkstyle, JaCoCo y Lombok

---

## 📦 Build & calidad

- `checkstyle.xml` con reglas personalizadas.
- `jacocoTestReport` con cobertura.
- `build.gradle` define dependencias y plugins.

---

## Adicionales

- Se coloco 3 campos adicionales en las tablas para auditoria:
```
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  enabled BIT DEFAULT 0
```
- Se genero validaciones en las consultas a base de datos para saber si el registro
estaba activo
```
(enabled = 1)
```
- Se aplico spring seurity para validar que viajen 2 cabecezar

```
    X-Request-ID (requerido) - abc123
    X-Correlation-ID (requerido) - trace-001
```

## 🙋‍♂️ Autor

Desarrollado por **Walter Abregu Tinoco**
Para proceso técnico de **BCNC Group**
