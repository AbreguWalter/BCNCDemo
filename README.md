# 🧠 BCNC Pricing API

API REST desarrollada como solución a la prueba técnica de BCNC Group, diseñada para consultar tarifas aplicables a productos de la marca ZARA en una fecha y hora determinadas. Implementada en Java 17 con Spring Boot 3.5.0, aplicando arquitectura hexagonal, programación funcional, buenas prácticas de testing, validaciones HTTP y documentación con OpenAPI 3.0.

---

## 🚀 Tecnologías y dependencias principales

- Java 17.0.15
- Spring Boot 3.5.0
- Spring Web, Spring Data JPA
- H2 in-memory database
- Lombok
- Checkstyle (custom rules)
- JaCoCo (cobertura de tests)
- Springdoc OpenAPI 3.0
- JUnit 5 + Mockito
- Gradle 8.12.1

---

## 📐 Arquitectura hexagonal

El proyecto está estructurado siguiendo arquitectura hexagonal (ports & adapters):

```
com.bcnc.princing.demo
├── application → lógica de negocio (casos de uso)
│ └── service
│ └── serviceImpl
├── config → configuración global, manejo de errores, seguridad
├── domain → modelo de dominio (Price)
│ └── model
│ └── port → interfaz de repositorio (puerto)
├── infrastructure → infraestructura externa
│ ├── adapter → implementación de puertos (repositorio JPA)
│ ├── controller → endpoints REST
│ │ └── dto → objetos de transporte
│ ├── entity → entidades JPA persistidas
│ └── mapper → conversores
└── DemoPricingApplication.java
```

---

## 🧪 Test coverage

Se cubren tests unitarios y de integración para:

- `PriceServiceImpl` (servicio)
- `PriceRepositoryAdapter` (adaptador)
- `PriceController` (MockMvc + mock de `PriceService`)
- `SpringDataPriceRepository` (con `@DataJpaTest` y H2)

Además, se han desarrollado los **5 tests funcionales exigidos** por la prueba para:

1. 14/06 a las 10:00
2. 14/06 a las 16:00
3. 14/06 a las 21:00
4. 15/06 a las 10:00
5. 16/06 a las 21:00

---

## 📄 Documentación OpenAPI

Se define el contrato en formato `openapi.yaml`, compatible con Swagger UI. Contiene:

- Endpoint `/api/prices`
- Headers requeridos (`X-Request-ID`, `X-Correlation-ID`)
- Ejemplos `200`, `404`, `500` documentados
- Esquema `PriceResponse`

Para visualizarla localmente:
```bash
http://localhost:8080/swagger-ui/index.html
```

---

## 🔧 Configuración de estilo y calidad

- `checkstyle.xml`: validaciones de estilo (nombres, indentación, braces, imports)
- `jacocoTestReport`: cobertura generada al correr los tests
- `build.gradle`: incluye configuración de OpenAPI, Checkstyle, JaCoCo y Lombok

---

## ⚙️ Endpoints disponibles

### `GET /api/prices`
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

#### Ejemplo de respuesta `200`
```json
{
  "product": "Product 35455",
  "brand": "ZARA",
  "priceList": "Afternoon promo",
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "currency": "EUR"
}
```

---

## ▶️ Ejecución local

1. Clona el repositorio
2. Ejecuta desde terminal:
```bash
./gradlew bootRun
```

3. API estará disponible en:
```bash
http://localhost:8080/api/prices
```

---

## 🧪 Ejecutar pruebas

```bash
./gradlew test
./gradlew jacocoTestReport
```

> El reporte HTML se genera en `build/reports/jacoco/test/html/index.html`

---

## 📌 Notas técnicas

- Las respuestas de error siguen el estándar:
```json
{
  "message": "No applicable price found.",
  "timestamp": "2025-05-23T22:18:42.9828077-05:00",
  "error": "NOT_FOUND"
}
```

---

## 🙋‍♂️ Autor

Desarrollado por Walter Abregu Tinoco como parte del proceso de selección para BCNC Group.
