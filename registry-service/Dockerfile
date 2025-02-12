# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/registry-service.jar registry-service.jar

# Expose the application port
EXPOSE 5551

# Command to run the application
ENTRYPOINT ["java", "-jar", "registry-service.jar"]
