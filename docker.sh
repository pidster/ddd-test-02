#!/bin/bash

# Make this script executable with: chmod +x docker.sh

# Function to display usage information
function show_usage {
  echo "Usage: ./docker.sh [COMMAND]"
  echo ""
  echo "Commands:"
  echo "  up           Start all services in detached mode"
  echo "  up-dev       Start development services in detached mode"
  echo "  down         Stop all services and remove containers"
  echo "  down-dev     Stop development services and remove containers"
  echo "  logs         View logs for all services"
  echo "  logs-dev     View logs for development services"
  echo "  ps           List running containers"
  echo "  build        Build all Docker images"
  echo "  clean        Remove all containers, networks, and volumes"
  echo "  help         Show this help message"
}

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
  echo "Docker is not installed. Please install Docker first."
  exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
  echo "Docker Compose is not installed. Please install Docker Compose first."
  exit 1
fi

# Process command
case "$1" in
  up)
    echo "Starting all services..."
    docker-compose up -d
    echo "All services started. Use './docker.sh logs' to view logs."
    ;;
  up-dev)
    echo "Starting development services..."
    docker-compose -f docker-compose.dev.yml up -d
    echo "Development services started. Use './docker.sh logs-dev' to view logs."
    ;;
  down)
    echo "Stopping all services..."
    docker-compose down
    ;;
  down-dev)
    echo "Stopping development services..."
    docker-compose -f docker-compose.dev.yml down
    ;;
  logs)
    docker-compose logs -f
    ;;
  logs-dev)
    docker-compose -f docker-compose.dev.yml logs -f
    ;;
  ps)
    docker-compose ps
    ;;
  build)
    echo "Building all Docker images..."
    docker-compose build
    ;;
  clean)
    echo "Removing all containers, networks, and volumes..."
    docker-compose down -v
    docker-compose -f docker-compose.dev.yml down -v
    ;;
  help|*)
    show_usage
    ;;
esac
