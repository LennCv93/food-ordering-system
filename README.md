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

- [`food-ordering-architecture-overview.md`](./food-ordering-architecture-overview.md) — contexto de negocio, diagramas y contratos completos (documento de referencia arquitectónica; prevalece ante cualquier conflicto de contenido de negocio).
- [`food-ordering-implementation-spec.md`](../food-ordering-implementation-spec.md) — guía de implementación paso a paso utilizada para construir este repositorio.
- [`architecture-diagram.svg`](./architecture-diagram.svg) — diagrama de arquitectura general.
- [`docs/infrastructure.md`](./docs/infrastructure.md) — puertos y URLs de acceso a toda la infraestructura (Kafka, Postgres, Zipkin, Prometheus, Grafana).
- [`docs/postman/`](./docs/postman/) — colección Postman con el flujo end-to-end completo.

## Prerrequisitos

- Docker y Docker Compose
- JDK 21
- Maven (o el wrapper `./mvnw` si el servicio lo incluye)
- Postman (opcional, para importar la colección de `docs/postman/`)
- Para el despliegue en Kubernetes: un cluster local con acceso al daemon de Docker donde se compilan las imágenes (esta guía usa **Kubernetes de Docker Desktop** — Settings → Kubernetes → Enable Kubernetes — porque comparte el mismo daemon y no requiere registry ni cargar imágenes aparte)

Antes de cualquiera de las dos opciones, compilar los 5 servicios (los `Dockerfile` copian el jar ya compilado, no compilan dentro de Docker):

```
for d in user-service catalog-service order-service payment-service delivery-service; do
  (cd "$d" && mvn clean package -DskipTests)
done
```

## Opción A — Levantar todo con Docker Compose

1. **Configurar variables de entorno** — copiar `.env.example` a `.env` en la raíz del repo y completar los valores (`JWT_SECRET`, `DB_USER`, `DB_PASSWORD`, `PAYMENT_SIMULATION_SUCCESS_RATE`):

   ```
   cp .env.example .env
   ```

2. **Levantar todo el stack** (los 5 microservicios + Postgres, Kafka, Zipkin, Prometheus y Grafana):

   ```
   docker compose up -d --build
   ```

3. **Verificar que todo esté saludable**:

   ```
   docker compose ps
   ```

   Postgres, Kafka y Zipkin exponen healthcheck; los 5 microservicios deben responder `200` en `/actuator/health` una vez arriba.

4. **Apagar el entorno**:

   ```
   docker compose down
   ```

   Agregar `-v` si además se quiere borrar el volumen de datos de Postgres (`docker compose down -v`) — esto elimina todos los usuarios, productos, pedidos, pagos y entregas creados. Kafka no tiene volumen persistente: sus topics se recrean vacíos en cada `down` + `up`.

## Opción B — Levantar todo con Kubernetes (Docker Desktop)

> En máquinas con poca RAM, el control plane de Kubernetes se suma al consumo de los 5 servicios + infraestructura — si el entorno se pone lento, considera aplicar solo un subconjunto de `k8s/infra/` (por ejemplo, sin `prometheus`/`grafana`) mientras iteras.

1. **Confirmar el contexto de kubectl**:

   ```
   kubectl config use-context docker-desktop
   ```

2. **Construir las imágenes** con el tag exacto que esperan los manifiestos (`<service>:latest`). Docker Desktop Kubernetes comparte el mismo daemon de Docker usado para compilar, así que con que la imagen exista localmente alcanza — los `Deployment` usan `imagePullPolicy: IfNotPresent`, no intentan bajarla de un registry:

   ```
   for d in user-service catalog-service order-service payment-service delivery-service; do
     docker build -t "$d:latest" "./$d"
   done
   ```

3. **Crear el namespace y aplicar todos los manifiestos** — los `Secret` ya vienen con credenciales de desarrollo precargadas (las mismas de `.env.example`, en base64), no hace falta editarlos para un primer intento:

   ```
   kubectl apply -f k8s/namespace.yaml
   kubectl apply -f k8s/ -R
   ```

4. **Verificar que todos los pods estén `Running`/`Ready`**:

   ```
   kubectl get pods -n food-ordering -w
   ```

   (Ctrl+C para salir del watch una vez que todo esté listo).

5. **Acceder a los servicios** — todos los `Service` son `ClusterIP`, así que hace falta `port-forward` (cada uno queda escuchando en el mismo puerto que en Docker Compose):

   ```
   kubectl port-forward -n food-ordering svc/user-service 8081:8081 &
   kubectl port-forward -n food-ordering svc/catalog-service 8082:8082 &
   kubectl port-forward -n food-ordering svc/order-service 8083:8083 &
   kubectl port-forward -n food-ordering svc/payment-service 8084:8084 &
   kubectl port-forward -n food-ordering svc/delivery-service 8085:8085 &
   kubectl port-forward -n food-ordering svc/zipkin 9411:9411 &
   kubectl port-forward -n food-ordering svc/prometheus 9090:9090 &
   kubectl port-forward -n food-ordering svc/grafana 3000:3000 &
   ```

   Con esto quedan accesibles las mismas URLs de las tablas de abajo (Swagger UI, Zipkin, Prometheus, Grafana).

6. **Apagar/limpiar** — elimina todos los recursos del namespace:

   ```
   kubectl delete -f k8s/ -R
   ```

   O, más simple, borrar el namespace completo (elimina todo, incluyendo el volumen de Postgres):

   ```
   kubectl delete namespace food-ordering
   ```

## Acceso a los servicios

| Servicio | Swagger UI |
|---|---|
| `user-service` | http://localhost:8081/swagger-ui.html |
| `catalog-service` | http://localhost:8082/swagger-ui.html |
| `order-service` | http://localhost:8083/swagger-ui.html |
| `payment-service` | http://localhost:8084/swagger-ui.html |
| `delivery-service` | http://localhost:8085/swagger-ui.html |

## Acceso a la infraestructura

| Componente | URL |
|---|---|
| Kafka (bootstrap) | `localhost:9092` |
| PostgreSQL | `localhost:5432` |
| Zipkin | http://localhost:9411 |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 |

Detalle completo (credenciales, healthchecks, troubleshooting) en [`docs/infrastructure.md`](./docs/infrastructure.md).

## Usuario admin sembrado

El sistema arranca con un usuario `ADMIN` de referencia (migración `V2__seed_admin_user.sql` de `user-service`):

- **email:** `admin@foodorder.com`
- **password:** `Admin123!`

## Probar el flujo completo

La forma más rápida de ejercitar el flujo end-to-end (registro → login → crear producto → crear pedido → pagar → entregar) es importando la colección Postman en [`docs/postman/`](./docs/postman/) y corriéndola con el Runner en orden. También se puede probar manualmente vía cada Swagger UI.
