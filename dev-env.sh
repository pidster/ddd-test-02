#!/bin/bash

# Make this script executable with: chmod +x dev-env.sh

# Function to display usage
show_usage() {
  echo "Usage: ./dev-env.sh [COMMAND]"
  echo ""
  echo "Commands:"
  echo "  start     Start the development environment"
  echo "  stop      Stop the development environment"
  echo "  logs      Show logs from the development environment"
  echo "  restart   Restart the development environment"
  echo "  status    Check the status of containers"
  echo "  clean     Remove all development containers and volumes"
  echo "  build     Rebuild the Docker image"
  echo "  help      Show this help message"
}

# Function to check Docker and Docker Compose installation
check_dependencies() {
  if ! command -v docker &> /dev/null; then
    echo "Error: Docker is not installed or not in PATH."
    echo "Please install Docker from https://docs.docker.com/get-docker/"
    exit 1
  fi

  if ! docker info &> /dev/null; then
    echo "Error: Docker daemon is not running or you don't have permissions."
    echo "Please start Docker and make sure you have the right permissions."
    exit 1
  fi

  if ! command -v docker-compose &> /dev/null; then
    echo "Warning: docker-compose command not found, falling back to 'docker compose'"
    DOCKER_COMPOSE="docker compose"
  else
    DOCKER_COMPOSE="docker-compose"
  fi
}

# Function to check if containers are running
check_status() {
  if [ "$($DOCKER_COMPOSE -f docker-compose.dev.yml ps -q)" ]; then
    echo "Development environment is running."
    $DOCKER_COMPOSE -f docker-compose.dev.yml ps
    
    # Check health status of claim service
    HEALTH=$(docker inspect --format='{{.State.Health.Status}}' claim-service-dev 2>/dev/null || echo "container not found")
    echo ""
    echo "Claim Service Health: $HEALTH"
    
    if [ "$HEALTH" = "healthy" ]; then
      echo "✅ The development environment is working correctly!"
      echo "Access the application at: http://localhost:8080"
      echo "Access H2 Console at: http://localhost:8080/h2-console"
      echo "   - JDBC URL: jdbc:h2:mem:claimdb"
      echo "   - Username: sa"
      echo "   - Password: (leave empty)"
      echo "Access Adminer at: http://localhost:8081"
    elif [ "$HEALTH" = "unhealthy" ]; then
      echo "❌ The claim service is unhealthy. Check the logs with: ./dev-env.sh logs"
    fi
  else
    echo "Development environment is not running."
  fi
}

# Main function to handle commands
main() {
  check_dependencies

  case "$1" in
    start)
      echo "Starting development environment..."
      $DOCKER_COMPOSE -f docker-compose.dev.yml up -d
      sleep 5
      check_status
      ;;
    stop)
      echo "Stopping development environment..."
      $DOCKER_COMPOSE -f docker-compose.dev.yml down
      ;;
    logs)
      echo "Showing logs..."
      $DOCKER_COMPOSE -f docker-compose.dev.yml logs -f
      ;;
    restart)
      echo "Restarting development environment..."
      $DOCKER_COMPOSE -f docker-compose.dev.yml down
      $DOCKER_COMPOSE -f docker-compose.dev.yml up -d
      sleep 5
      check_status
      ;;
    status)
      check_status
      ;;
    clean)
      echo "Cleaning up development environment..."
      $DOCKER_COMPOSE -f docker-compose.dev.yml down -v --remove-orphans
      echo "Development environment cleaned."
      ;;
    build)
      echo "Building Docker image..."
      $DOCKER_COMPOSE -f docker-compose.dev.yml build --no-cache
      echo "Docker image rebuilt."
      ;;
    help|*)
      show_usage
      ;;
  esac
}

# Run the script
main "$@"
