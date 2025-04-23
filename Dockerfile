FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app

# Copy POM file first for dependency resolution (better caching)
COPY pom.xml .

# Check if Maven wrapper exists, otherwise use Maven from image
COPY .mvn .mvn 2>/dev/null || :
COPY mvnw mvnw 2>/dev/null || :

# Make the Maven wrapper executable if it exists
RUN if [ -f "mvnw" ]; then chmod +x mvnw; fi

# Use Maven wrapper if exists, otherwise use system Maven
RUN if [ -f "mvnw" ]; then \
      ./mvnw dependency:go-offline; \
    else \
      apt-get update && \
      apt-get install -y maven && \
      mvn dependency:go-offline; \
    fi

# Copy source code
COPY src src

# Build the application
RUN if [ -f "mvnw" ]; then \
      ./mvnw package -DskipTests; \
    else \
      mvn package -DskipTests; \
    fi

# Use a smaller runtime image
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the built JAR file
COPY --from=builder /app/target/claimprocessing-*.jar app.jar

# Add healthcheck
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Expose application port
EXPOSE 8080

# Set Java options for containers
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseContainerSupport"

# Add support for dev environment with Spring profiles
ENV SPRING_PROFILES_ACTIVE=dev

# Install curl for healthcheck
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=$SPRING_PROFILES_ACTIVE -jar app.jar"]
