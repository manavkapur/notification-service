# Use a lightweight Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy all files
COPY . .

# Build the JAR (skip tests to speed up build)
RUN ./mvnw clean package -DskipTests

# Expose the default port for the service
EXPOSE 8085

# Run the service
ENTRYPOINT ["java", "-jar", "target/notification-service-0.0.1-SNAPSHOT.jar"]
