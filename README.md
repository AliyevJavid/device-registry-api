# Device Registry API

This is a Spring Boot-based REST API for managing devices. It provides endpoints to add, update, delete, and retrieve devices, as well as search for devices by their brand.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
   - [Build and Run Locally](#build-and-run-locally)
   - [Running Tests](#running-tests)
   - [Using Docker](#using-docker)
- [OpenAPI Documentation](#openapi-documentation)
- [API Endpoints](#api-endpoints)
- [Future Improvements](#future-improvements)

## Features

- **Add Device**: Create a new device with a name and brand.
- **Retrieve Device**: Fetch a device by its unique ID.
- **List Devices**: Retrieve all devices with pagination.
- **Update Device**: Fully or partially update a device's details.
- **Delete Device**: Remove a device from the database.
- **Search Devices**: Search for devices by brand with pagination support.

## Technologies

- **Java**: 21
- **Spring Boot**: 3.x
- **Gradle**: Build tool
- **JUnit 5** and **Mockito**: For testing
- **H2 Database**: In-memory database for development and testing
- **Swagger/OpenAPI**: For API documentation
- **Docker**: For containerization

## Prerequisites

- Java 21
- Gradle 7.x or higher
- Docker (optional, if running in a container)

## Getting Started

### Build and Run Locally

Follow these steps to set up and run the project locally:

1. **Build the project**:
    ```bash
    ./gradlew clean build
    ```

2. **Run the application**:
    ```bash
    ./gradlew bootRun
    ```

   The service will be running at `http://localhost:8080`.

### Running Tests

1. **Run all tests**:
    ```bash
    ./gradlew test
    ```

### Using Docker

To containerize and run the project using Docker:

1. **Build the Docker image**:
    ```bash
    docker build -t device-registry-api .
    ```

2. **Run the Docker container**:
    ```bash
    docker run -p 8080:8080 device-registry-api
    ```

   The application will be accessible at `http://localhost:8080`.

## OpenAPI Documentation

The API includes OpenAPI documentation for easy exploration and testing of endpoints.

- Swagger UI: Visit `http://localhost:8080/swagger-ui.html` to view and test the API using the Swagger UI.
- OpenAPI JSON: Access the OpenAPI JSON specification at `http://localhost:8080/v3/api-docs`.

These resources provide interactive documentation to explore the API functionality.

## API Endpoints

### Base URL: `http://localhost:8080/api/v1/devices`

| Method | Endpoint               | Description                         |
|--------|------------------------|-------------------------------------|
| POST   | `/`                    | Add a new device                    |
| GET    | `/{id}`                | Get a device by ID                  |
| GET    | `/`                    | List all devices (paginated)        |
| PUT    | `/{id}`                | Update an existing device           |
| PATCH  | `/{id}`                | Partially update a device           |
| DELETE | `/{id}`                | Delete a device                     |
| GET    | `/search?brand={name}` | Search devices by brand (paginated) |

## Future Improvements

1. **Separate Brand Management**:
   - Create a separate entity and endpoints for managing brands. Devices will reference brands, improving consistency and enabling brand-level operations.

2. **Enhanced OpenAPI Documentation**:
   - Add detailed examples and descriptions for each endpoint in the Swagger UI.

3. **Database Migration**:
   - Add Flyway or Liquibase for database migration support.

4. **Security**:
   - Integrate authentication and authorization (e.g., OAuth2, JWT).

5. **Persistent Database**:
   - Replace the in-memory H2 database with a production-ready database like PostgreSQL or MySQL.

6. **CI/CD Integration**:
   - Automate testing and deployment pipelines using GitHub Actions.
