# Trakr

A budget management and expenses tracker REST API built with Spring Boot.

## Overview

Trakr is a REST API application for managing budgets and tracking expenses. Features JWT-based authentication, user management, and budget operations with PostgreSQL persistence.

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Data JPA** - Database operations
- **Spring Web** - REST API endpoints
- **Spring Security** - Authentication & authorization
- **Spring Boot Actuator** - Application monitoring
- **Spring Validation** - Input validation
- **PostgreSQL** - Production database
- **H2 Database** - Testing database
- **JWT (JJWT)** - Token-based authentication
- **MapStruct** - Object mapping
- **Lombok** - Code generation
- **Maven** - Build tool

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL (for production)

## Getting Started

### Clone the Repository

```bash
git clone <repository-url>
cd trakr
```

### Build the Project

```bash
./mvnw clean install
```

### Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## Features

- **User Management**: Registration, authentication, and profile management
- **JWT Authentication**: Secure token-based authentication
- **Budget Management**: Create, read, update, and delete budgets
- **RESTful API**: Clean REST endpoints with proper HTTP status codes
- **Exception Handling**: Centralized error handling with custom exceptions
- **Data Validation**: Input validation with custom DTOs
- **Pagination**: Paginated responses for list endpoints

## API Endpoints

### Authentication
- `POST /auth/login` - User login

### Users
- `GET /users` - Get all users (paginated)
- `GET /users/{id}` - Get user by ID
- `POST /users` - Create new user
- `PUT /users/{id}` - Update user
- `DELETE /users/{id}` - Delete user

### Budgets
- `GET /budgets` - Get all budgets (paginated)
- `GET /budgets/{id}` - Get budget by ID
- `POST /budgets` - Create new budget
- `PUT /budgets/{id}` - Update budget
- `DELETE /budgets/{id}` - Delete budget

## Configuration

The application uses `application.yaml` for configuration. Key settings:

- Application name: `trakr`
- PostgreSQL database connection
- JWT secret configuration
- JPA/Hibernate settings

## Development

### Project Structure

```
src/
├── main/
│   ├── java/app/vercel/ingenio_theta/trakr/
│   │   ├── auth/                    # Authentication & JWT
│   │   ├── budgets/                 # Budget management
│   │   ├── config/                  # Security configuration
│   │   ├── shared/                  # Shared utilities
│   │   │   ├── dto/                 # Common DTOs
│   │   │   ├── exceptions/          # Exception handling
│   │   │   └── response/            # Response wrappers
│   │   ├── users/                   # User management
│   │   └── TrakrApplication.java
│   └── resources/
│       └── application.yaml
└── test/
    ├── java/app/vercel/ingenio_theta/trakr/
    │   ├── users/                   # User tests
    │   └── TrakrApplicationTests.java
    └── resources/
        └── application.yaml
```

### Running Tests

```bash
./mvnw test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests
5. Submit a pull request

## License

This project is licensed under the terms specified in the pom.xml file.