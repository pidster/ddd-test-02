# Lakeside Mutual Claim Processing

A Domain-Driven Design (DDD) based microservices application for insurance claim processing.

## Architecture

The system is composed of the following microservices:

- **Claim Service**: Core service for claims management
- **Policy Holder Service**: Manages policy holder information
- **Notification Service**: Handles notifications to various stakeholders
- **API Gateway**: Single entry point for all client requests
- **Discovery Server**: Service registry for service discovery
- **Config Server**: Centralized configuration management

## Running the Application

### Prerequisites

- Docker and Docker Compose
- Java 17 (for local development without Docker)
- Maven (for local development without Docker)

### Using Docker Compose

To run the complete microservices architecture:

```bash
# Build and start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

### For Development

For local development with minimal dependencies:

```bash
# Run with development configuration
docker-compose -f docker-compose.dev.yml up -d

# Stop development services
docker-compose -f docker-compose.dev.yml down
```

### Access Services

- Claim Service: http://localhost:8080
- Policy Holder Service: http://localhost:8081
- Notification Service: http://localhost:8082
- API Gateway: http://localhost:9000
- Eureka Dashboard: http://localhost:8761
- RabbitMQ Management: http://localhost:15672 (guest/guest)
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000 (admin/admin)

## Development Guide

### Project Structure

```
claim-processing/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/lakesidemutual/claimprocessing/
│   │   │       ├── application/     # Application services & DTOs
│   │   │       ├── domain/          # Domain model, repositories & events
│   │   │       ├── infrastructure/  # Technical implementations
│   │   │       └── interfaces/      # Controllers & views
│   │   └── resources/               # Configuration files
│   └── test/                        # Test classes
├── config-server/                   # Configuration server
├── api-gateway/                     # API Gateway
├── policyholder-service/            # Policy holder microservice
├── notification-service/            # Notification microservice
├── prometheus/                      # Prometheus configuration
├── docker-compose.yml               # Docker Compose file
└── docker-compose.dev.yml           # Docker Compose for development
```

### Building Docker Images Manually

```bash
# Build the Claim Service image
docker build -t lakesidemutual/claimprocessing:0.0.1-SNAPSHOT .

# Run a specific service
docker run -p 8080:8080 lakesidemutual/claimprocessing:0.0.1-SNAPSHOT
```

## Technologies

- Spring Boot
- Spring Cloud (Eureka, Config Server)
- Spring Data JPA
- PostgreSQL
- RabbitMQ
- Docker & Docker Compose
- Prometheus & Grafana for monitoring
