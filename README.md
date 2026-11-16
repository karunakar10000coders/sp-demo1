# Spring Boot REST API Application

A clean, modular Spring Boot 3 application providing REST APIs for **Posts**, **Products**, and **Users**.

## Requirements
- Java 21+
- Apache Maven 3.9+

---

## Getting Started

### 1. Run with Maven
```powershell
mvn spring-boot:run
```

### 2. Run with Executable JAR
```powershell
mvn clean package -DskipTests
java -jar target/springboot-basic-api-1.0.0.jar
```

The application starts on port `8080` by default.

---

## API Endpoints Overview

Root info endpoint:
- `GET http://localhost:8080/`

### 1. Posts API (`/posts` or `/api/posts`)
- `GET /posts` - List all posts (optional query param: `?author=Alice`)
- `GET /posts/{id}` - Get post by ID
- `POST /posts` - Create a new post
- `PUT /posts/{id}` - Update an existing post
- `DELETE /posts/{id}` - Delete a post

#### Example Create Post:
```json
POST /posts
Content-Type: application/json

{
  "title": "Spring Boot REST Best Practices",
  "content": "Use standard HTTP status codes and uniform JSON response formats.",
  "author": "Alice"
}
```

---

### 2. Products API (`/products` or `/api/products`)
- `GET /products` - List all products (optional query param: `?category=Electronics`)
- `GET /products/{id}` - Get product by ID
- `POST /products` - Create a new product
- `PUT /products/{id}` - Update an existing product
- `DELETE /products/{id}` - Delete a product

#### Example Create Product:
```json
POST /products
Content-Type: application/json

{
  "name": "Wireless Ergonomic Mouse",
  "description": "Rechargeable Bluetooth optical mouse with thumb scroll",
  "price": 59.99,
  "category": "Electronics",
  "stockQuantity": 150
}
```

---

### 3. Users API (`/users` or `/api/users`)
- `GET /users` - List all users (optional query param: `?active=true`)
- `GET /users/{id}` - Get user by ID
- `POST /users` - Register a new user
- `PUT /users/{id}` - Update user details
- `DELETE /users/{id}` - Delete a user

#### Example Create User:
```json
POST /users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "role": "USER",
  "active": true
}
```

---

## Features
- **In-Memory Thread-Safe Data Store**: Initialized with sample seed data.
- **DTO Validation**: Uses Bean Validation annotations (`@NotBlank`, `@NotNull`, `@Email`, `@Min`, `@DecimalMin`, etc.).
- **Global Exception Handling**: Centralized controller advice returning consistent JSON errors for 404, 400 validation errors, and 500.
- **Automated Tests**: MockMvc integration tests verifying all core endpoints.
