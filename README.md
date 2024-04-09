# Laboration 1: Restful Web Service with JAX-RS and WildFly

This project implements a Restful Web service using the JAX-RS API, running on a WildFly application server version 31.0.0.Final, and utilizing the Jakarta EE 10.0 specification. The project includes CRUD operations for an entity, with data exchanged in JSON format between client and server.

## Installation

Before running the Docker build, ensure you have Maven installed. Then follow these steps:

1. Run `mvn clean package` in the terminal.
2. Run `docker build --tag wildfly:api .` to build the Docker image.

## Usage

To use the project, follow these steps:

1. Ensure Docker is installed on your system.
2. Build the Docker image as described in the installation steps.
3. Run the Docker container using the built image.

## Features

- Implementation of CRUD operations for the entity.
- Use of DTOs to serialize and deserialize data between client and server.
- Error handling and meaningful response codes.
- Validation of incoming data using Jakarta Bean Validation.
- Unit tests and integration tests for endpoints.
- Dockerfile for easy deployment as a Docker container.
- Docker Compose file for setting up the database and running WildFly with the application.

## Testing

We use [JUnit](https://junit.org/junit5/) for testing the endpoints. To run the tests, use the following command:

```bash
mvn test
