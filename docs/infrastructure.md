# Infraestructura — Puertos y URLs de acceso

Todas las URLs de esta página son válidas tanto levantando el entorno con **Docker Compose** (los puertos quedan expuestos directamente en `localhost`) como con **Kubernetes** (una vez corridos los `kubectl port-forward` descritos en el [README](../README.md), que mapean cada `Service` al mismo puerto de `localhost`).

## Infraestructura compartida

| Componente | Puerto(s) | URL de acceso | Notas |
|---|---|---|---|
| Kafka (broker) | 9092 (cliente), 9093 (controller interno) | `localhost:9092` | Bootstrap server para productores/consumidores externos al stack. Imagen `apache/kafka:3.7.0`, modo KRaft, broker único. No tiene volumen persistente: sus topics se recrean vacíos cada vez que el contenedor se recrea. |
| PostgreSQL | 5432 | `localhost:5432` | Una sola instancia con 5 bases: `user_db`, `catalog_db`, `order_db`, `payment_db`, `delivery_db`. Credenciales en `.env` (`DB_USER` / `DB_PASSWORD`), las mismas para las 5 bases. |
| Zipkin | 9411 | http://localhost:9411 | UI de trazas distribuidas. Traza de referencia: creación de un pedido (`order-service → catalog-service`). |
| Prometheus | 9090 | http://localhost:9090 | UI y estado de los targets en http://localhost:9090/targets |
| Grafana | 3000 | http://localhost:3000 | Usuario/password por defecto: `admin` / `admin` (pide cambiarla en el primer login). Dashboard provisionado: "Food Ordering System - Overview". |

## Microservicios

| Servicio | Puerto | Swagger UI | Health | Métricas Prometheus |
|---|---|---|---|---|
| `user-service` | 8081 | http://localhost:8081/swagger-ui.html | http://localhost:8081/actuator/health | http://localhost:8081/actuator/prometheus |
| `catalog-service` | 8082 | http://localhost:8082/swagger-ui.html | http://localhost:8082/actuator/health | http://localhost:8082/actuator/prometheus |
| `order-service` | 8083 | http://localhost:8083/swagger-ui.html | http://localhost:8083/actuator/health | http://localhost:8083/actuator/prometheus |
| `payment-service` | 8084 | http://localhost:8084/swagger-ui.html | http://localhost:8084/actuator/health | http://localhost:8084/actuator/prometheus |
| `delivery-service` | 8085 | http://localhost:8085/swagger-ui.html | http://localhost:8085/actuator/health | http://localhost:8085/actuator/prometheus |

## Troubleshooting

- **Listar topics de Kafka:**
  ```
  docker compose exec kafka /opt/kafka/bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
  ```
- **Ver los mensajes de un topic desde el principio** (útil para depurar el flujo de eventos):
  ```
  docker compose exec kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic order.created --from-beginning
  ```
- **Ver logs de un servicio puntual:**
  ```
  docker compose logs -f <servicio>
  ```
- **Ver el estado del circuit breaker `order-service → catalog-service`:**
  ```
  curl -s http://localhost:8083/actuator/prometheus | grep resilience4j_circuitbreaker_state
  ```
- Si Kafka se recreó (`docker compose up --force-recreate kafka`, o un `down` + `up`), sus topics y grupos de consumidores quedan vacíos — es esperado, ya que no tiene volumen persistente; se recrean automáticamente en cuanto un productor/consumidor los usa.
- En Kubernetes, si un pod queda en `ImagePullBackOff`, revisar que la imagen se haya construido con el tag exacto `<service>:latest` (ver [README](../README.md), Opción B) y que `imagePullPolicy: IfNotPresent` esté presente en el `Deployment`.
