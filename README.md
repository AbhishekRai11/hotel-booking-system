# Hotel Booking System

A portfolio-ready Spring Boot REST API for hotel discovery, room availability, secure booking, cancellation and admin operations.

## Highlights
- Java 17 + Spring Boot + Maven
- JWT authentication with USER / ADMIN roles
- Spring Data JPA / Hibernate + MySQL
- Redis caching for hotel reads
- Date-range availability and overlap protection
- Booking lifecycle and payment status
- Bean Validation + global exception handling
- OpenAPI / Swagger UI
- Docker Compose for application, MySQL and Redis
- Actuator health endpoint

## Architecture
Controller -> Service -> Repository -> MySQL, with Redis used as a cache layer. JWT is validated by a stateless Spring Security filter.

## API
| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |
| GET | `/api/hotels` | Authenticated |
| POST | `/api/hotels` | ADMIN |
| GET | `/api/hotels/{hotelId}/rooms` | Authenticated |
| GET | `/api/hotels/{hotelId}/rooms/available?checkIn=2026-10-01&checkOut=2026-10-05` | Authenticated |
| POST | `/api/hotels/{hotelId}/rooms` | ADMIN |
| POST | `/api/bookings` | USER |
| GET | `/api/bookings/me` | USER |
| PUT | `/api/bookings/{id}/cancel` | USER |
| GET | `/api/admin/bookings` | ADMIN |
| PUT | `/api/admin/bookings/{id}/payment` | ADMIN |

Swagger: `http://localhost:8080/swagger-ui.html`

## Authentication flow
1. Register or login.
2. Receive a JWT.
3. Send `Authorization: Bearer <token>` on protected requests.
4. Spring Security validates the token and applies the user's role.

## Booking flow
A booking checks that the date range is valid, verifies that no confirmed booking overlaps the requested interval, calculates total price from nights × room rate, and stores payment status as `PENDING`.

## Redis
Redis caches hotel list reads for a short TTL. Cache entries are evicted when admin hotel changes are made, reducing repeated database reads while keeping updates visible.

## Run locally
Prerequisites: JDK 17, Maven 3.9+, MySQL 8+, Redis 7+.

```bash
cp .env.example .env
mvn clean test
mvn spring-boot:run
```

## Docker
```bash
docker compose up --build
```

## Demo admin
The development seed creates:
- Email: `admin@hotel.local`
- Password: `Admin@12345`

Change/remove this initializer for production deployments. Never use demo credentials in a real environment.

## Environment variables
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `REDIS_HOST`, `REDIS_PORT`, `JWT_SECRET`, `JWT_EXPIRATION_MS`, `SERVER_PORT`.

## Project structure
```text
src/main/java/com/abhishek/hotelbooking/
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── security
└── service/impl
```

## Database design
`users` 1-to-many `bookings`; `hotels` 1-to-many `rooms`; `rooms` 1-to-many `bookings`. Booking dates are indexed with room ID to support overlap checks.

## Screenshots / Swagger
Run the application and open Swagger UI to capture endpoint documentation for a portfolio README.

## Future enhancements
Payment gateway integration, refresh tokens, email notifications, optimistic/pessimistic locking strategy for high-contention booking, Flyway migrations, Testcontainers integration tests, pagination, search filters and CI/CD deployment.

## Security notes
Secrets are environment-driven. Do not commit `.env`, real credentials or production JWT secrets.
