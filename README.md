# Trakr

A budget management and expenses tracker application built with Spring Boot.

## Overview

Trakr is a web-based application designed to help users manage their budgets and track expenses efficiently. Built using modern Java technologies and Spring Boot framework.

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.7**
- **Spring Data JPA** - Database operations
- **Spring Web** - REST API endpoints
- **Spring Boot Actuator** - Application monitoring
- **PostgreSQL** - Production database
- **H2 Database** - Testing database
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

## Configuration

The application uses `application.properties` for configuration. Key settings:

- Application name: `trakr`
- Default profile uses H2 for testing
- Production profile should be configured with PostgreSQL

## Development

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── app/vercel/ingenio_theta/trakr/
│   │       └── TrakrApplication.java
│   └── resources/
│       ├── application.properties
│       ├── static/
│       └── templates/
└── test/
    └── java/
        └── app/vercel/ingenio_theta/trakr/
            └── TrakrApplicationTests.java
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