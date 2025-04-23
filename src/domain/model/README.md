# Domain Model

This directory contains the core domain model for the application, including:

- Entities: Objects that have an identity and are tracked through their lifecycle
- Value Objects: Immutable objects that describe characteristics
- Aggregates: Clusters of entities and value objects with a root entity
- Domain Events: Events that capture domain changes
- Domain Services: Operations that don't belong to a single entity

The domain model is the heart of the system and should be free of infrastructure concerns.
