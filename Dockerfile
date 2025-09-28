# --- Stage 1: Build the application using Maven ---
# Use a base image with Maven and Java 17 to build the project
FROM maven:3.9-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file first to leverage Docker's layer caching
# If pom.xml doesn't change, Docker won't re-download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Package the application, skipping the tests to speed up the build
RUN mvn clean package -DskipTests


# --- Stage 2: Create the final, lightweight image ---
# Use a lean base image with only the Java Runtime Environment
FROM eclipse-temurin:17-jre-jammy

# Set the working directory
WORKDIR /app

# Copy the packaged .jar file from the 'builder' stage
# The path is inside the target folder of the builder stage
COPY --from=builder /app/target/quickpoll-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# The command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]