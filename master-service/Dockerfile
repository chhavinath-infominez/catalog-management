# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/master-service.jar master-service.jar

# Expose the application port
EXPOSE 5552

# Command to run the application
ENTRYPOINT ["java", "-jar", "master-service.jar"]
