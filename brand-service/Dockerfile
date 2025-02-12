# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/brand-service.jar brand-service.jar

# Expose the application port
EXPOSE 5553

# Command to run the application
ENTRYPOINT ["java", "-jar", "brand-service.jar"]
