# Tomato - Food Delivery Platform

A production-grade food delivery platform (Zomato clone) built with Spring Boot and React.

## Tech Stack

| Layer | Technologies |
|-------|-------------|
| **Backend** | Java 17, Spring Boot 3.x, Spring Security, JPA/Hibernate, PostgreSQL |
| **Frontend** | React 18, Vite, TailwindCSS, React Router v6, Axios, Zustand |
| **Auth** | JWT + BCrypt |
| **Database** | PostgreSQL 15 (Docker) |

## Project Structure

```
tomato/
├── docker-compose.yml      # PostgreSQL container
├── tomato-backend/         # Spring Boot API
├── tomato-frontend/        # React SPA
├── README.md
└── ISSUES.md               # 35 open feature issues
```

## Prerequisites

- Java 17+
- Maven 3.8+
- Node.js 18+
- Docker & Docker Compose

## Quick Start

### 1. Start PostgreSQL

```bash
docker compose up -d
```

### 2. Run Backend

```bash
cd tomato-backend
mvn spring-boot:run
```

Backend runs at `http://localhost:8080`

### 3. Run Frontend

```bash
cd tomato-frontend
npm install
npm run dev
```

Frontend runs at `http://localhost:5173` (proxies `/api` to backend)

## Seed Data

On first run, the database is seeded with:

| Type | Details |
|------|---------|
| **Restaurants** | 10 across Mumbai, Delhi, Bangalore |
| **Menu Items** | 40 (4 per restaurant) |
| **Admin** | admin@tomato.com / Admin@123 |
| **User** | user@tomato.com / User@123 |

> **Note:** `ddl-auto: create-drop` resets the schema on every backend restart and re-seeds data. Suitable for development only.

## API Documentation

All endpoints return: `{ "success": boolean, "data": T, "message": string }`

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login, returns JWT |
| GET | `/api/auth/me` | JWT | Get current user |
| GET | `/api/restaurants` | Public | Paginated list (`?city=&cuisine=&page=0&size=10`) |
| GET | `/api/restaurants/{id}` | Public | Restaurant detail |
| POST | `/api/restaurants` | ADMIN | Create restaurant |
| PUT | `/api/restaurants/{id}` | ADMIN | Update restaurant |
| DELETE | `/api/restaurants/{id}` | ADMIN | Delete restaurant |
| GET | `/api/restaurants/{id}/menu` | Public | Menu items for restaurant |
| POST | `/api/restaurants/{id}/menu` | ADMIN | Add menu item |
| PUT | `/api/menu/{itemId}` | ADMIN | Update menu item |
| DELETE | `/api/menu/{itemId}` | ADMIN | Delete menu item |
| GET | `/api/cart` | JWT | Get user's cart |
| POST | `/api/cart/add` | JWT | Add item `{ menuItemId, quantity }` |
| PUT | `/api/cart/update` | JWT | Update quantity |
| DELETE | `/api/cart/remove/{itemId}` | JWT | Remove item |
| DELETE | `/api/cart/clear` | JWT | Clear cart |

## Running Tests

```bash
cd tomato-backend
mvn test
```

Includes `AuthServiceTest` and `CartServiceTest`.

## Feature Roadmap

| Status | Features |
|--------|----------|
| **Built** | Auth (register/login/JWT), Restaurant listing & detail, Menu by category, Cart CRUD, Admin restaurant/menu APIs, Seed data, Responsive UI |
| **Open** | Search & filters (ISSUE-001–006), Payment/checkout (007–012), Order tracking (013–017), Reviews (018–022), Recommendations (023–028), UI polish (029–035) |

See [ISSUES.md](ISSUES.md) for full details on all 35 open issues.

## Configuration

Backend config in `tomato-backend/src/main/resources/application.yml`:

```yaml
spring.datasource.url: jdbc:postgresql://localhost:5432/tomato_db
spring.datasource.username: postgres
spring.datasource.password: postgres
server.port: 8080
app.jwt.secret: <configured>
app.jwt.expiration: 86400000  # 24 hours
```
