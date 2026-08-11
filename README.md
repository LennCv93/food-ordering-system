# Food Ordering System

Sistema de pedidos de comida basado en microservicios, construido con Java 21, Spring Boot 3.5.6 y Clean Architecture. Cada servicio es independiente (su propio `pom.xml`, `.gitignore` y `Dockerfile`), se comunica de forma síncrona vía REST (order-service → catalog-service) y de forma asíncrona vía Kafka para la orquestación del ciclo de vida del pedido (creación, pago y entrega).

## Servicios

| Servicio | Puerto | Responsabilidad |
|---|---|---|
| `user-service` | 8081 | Registro/login de usuarios, emisión de JWT, gestión de lectura de usuarios |
| `catalog-service` | 8082 | CRUD de productos del catálogo |
| `order-service` | 8083 | Creación y ciclo de vida del pedido; consume `catalog-service`; publica/consume eventos Kafka |
| `payment-service` | 8084 | Simulación de procesamiento de pagos |
| `delivery-service` | 8085 | Gestión y simulación del ciclo de entrega |

## Documentación de referencia

- [`food-ordering-architecture-overview.md`](../food-ordering-architecture-overview.md) — contexto de negocio, diagramas y contratos completos (documento de referencia arquitectónica; prevalece ante cualquier conflicto de contenido de negocio).
- [`food-ordering-implementation-spec.md`](../food-ordering-implementation-spec.md) — guía de implementación paso a paso utilizada para construir este repositorio.
