# Trakr

A comprehensive budget management and financial tracker REST API built with Spring Boot.

## Overview

Trakr is a full-featured REST API application for managing personal finances including budgets, expenses, and income tracking. Features JWT-based authentication, user management, and comprehensive financial operations with PostgreSQL persistence and OpenAPI documentation.

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.9**
- **Spring Data JPA** - Database operations
- **Spring Web** - REST API endpoints
- **Spring Security** - Authentication & authorization
- **Spring Boot Actuator** - Application monitoring
- **Spring Validation** - Input validation
- **PostgreSQL** - Production database
- **H2 Database** - Testing database
- **JWT (JJWT 0.11.5)** - Token-based authentication
- **MapStruct 1.5.5** - Object mapping
- **Lombok** - Code generation
- **SpringDoc OpenAPI 2.8.6** - API documentation
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

### Database Setup

Ensure PostgreSQL is running on port 5433 with:

- Database: `trakr`
- Username: `trakr-user`
- Password: `securepassword`

### Build the Project

```bash
./mvnw clean install
```

### Run the Application

```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

### Access API Documentation

Once running, visit `http://localhost:8080/swagger-ui.html` for interactive API documentation.

## Features

- **User Management**: Registration, authentication, and profile management
- **JWT Authentication**: Secure token-based authentication with custom filters
- **Budget Management**: Create, read, update, and delete budgets
- **Expense Tracking**: Comprehensive expense management with categories
- **Income Management**: Track various income sources and amounts
- **RESTful API**: Clean REST endpoints with proper HTTP status codes
- **Exception Handling**: Centralized error handling with custom exceptions
- **Data Validation**: Input validation with custom DTOs and validators
- **Pagination**: Paginated responses for list endpoints
- **OpenAPI Documentation**: Interactive API documentation with Swagger UI
- **Audit Trail**: Automatic tracking of creation and modification timestamps
- **Category Management**: Predefined categories for expenses and income sources

## API Endpoints

### Authentication

- `POST /auth/register` - User registration
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

### Expenses

- `GET /expenses` - Get all expenses (paginated with filtering)
- `GET /expenses/{id}` - Get expense by ID
- `POST /expenses` - Create new expense
- `PUT /expenses/{id}` - Update expense
- `DELETE /expenses/{id}` - Delete expense

### Incomes

- `GET /incomes` - Get all incomes (paginated with filtering)
- `GET /incomes/{id}` - Get income by ID
- `POST /incomes` - Create new income
- `PUT /incomes/{id}` - Update income
- `DELETE /incomes/{id}` - Delete income

### API Documentation

- `GET /swagger-ui.html` - Interactive API documentation
- `GET /v3/api-docs` - OpenAPI specification

## Configuration

The application uses `application.yaml` for configuration. Key settings:

- Application name: `trakr`
- PostgreSQL database connection (localhost:5433)
- JWT secret configuration
- JPA/Hibernate settings with DDL auto-update
- Database credentials: `trakr-user` / `securepassword`

## Development

### Project Structure

```
src/
├── main/
│   ├── java/app/vercel/ingenio_theta/trakr/
│   │   ├── auth/                    # Authentication & JWT
│   │   │   ├── dtos/                # Auth DTOs
│   │   │   ├── AuthController.java  # Auth endpoints
│   │   │   ├── AuthService.java     # Auth business logic
│   │   │   ├── JwtService.java      # JWT token handling
│   │   │   └── ...                  # Other auth components
│   │   ├── budgets/                 # Budget management
│   │   │   ├── dtos/                # Budget DTOs
│   │   │   ├── Budget.java          # Budget entity
│   │   │   ├── BudgetController.java# Budget endpoints
│   │   │   └── ...                  # Other budget components
│   │   ├── expenses/                # Expense management
│   │   │   ├── dtos/                # Expense DTOs
│   │   │   ├── models/              # Expense entities
│   │   │   │   ├── Expense.java     # Expense entity
│   │   │   │   └── ExpenseCategory.java # Expense categories
│   │   │   ├── ExpenseController.java # Expense endpoints
│   │   │   └── ...                  # Other expense components
│   │   ├── incomes/                 # Income management
│   │   │   ├── dtos/                # Income DTOs
│   │   │   ├── models/              # Income entities
│   │   │   │   ├── Income.java      # Income entity
│   │   │   │   └── IncomeSource.java # Income sources
│   │   │   ├── IncomeController.java # Income endpoints
│   │   │   └── ...                  # Other income components
│   │   ├── config/                  # Security & OpenAPI configuration
│   │   │   ├── SecurityConfig.java  # Security configuration
│   │   │   ├── JwtFilter.java       # JWT authentication filter
│   │   │   └── OpenApiConfig.java   # OpenAPI configuration
│   │   ├── shared/                  # Shared utilities
│   │   │   ├── dto/                 # Common DTOs
│   │   │   ├── exceptions/          # Exception handling
│   │   │   ├── response/            # Response wrappers
│   │   │   └── validators/          # Custom validators
│   │   ├── users/                   # User management
│   │   │   ├── dtos/                # User DTOs
│   │   │   ├── User.java            # User entity
│   │   │   ├── UserController.java  # User endpoints
│   │   │   └── ...                  # Other user components
│   │   └── TrakrApplication.java
│   └── resources/
│       └── application.yaml
└── test/
    ├── java/app/vercel/ingenio_theta/trakr/
    │   ├── auth/                    # Auth tests
    │   ├── expenses/                # Expense tests
    │   ├── incomes/                 # Income tests
    │   ├── users/                   # User tests
    │   └── TrakrApplicationTests.java
    └── resources/
        └── application.yaml
```

### Running Tests

```bash
./mvnw test
```

## Data Models

### Expense Categories

- TRANSPORTATION
- FOOD
- UTILITIES
- ENTERTAINMENT
- OTHER

### Income Sources

- SALARY
- FREELANCE
- INVESTMENT
- BUSINESS
- OTHER

## API Documentation

Once the application is running, you can access the interactive API documentation at:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Testing

The project includes comprehensive unit tests for:

- Authentication services
- User management
- Expense tracking
- Income management

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
