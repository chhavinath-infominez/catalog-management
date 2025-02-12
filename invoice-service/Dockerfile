# Use OpenJDK as the base image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/auth-service.jar auth-service.jar

# Expose the application port
EXPOSE 5553

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
