# Build Stage
FROM maven:3.9.9-eclipse-temurin-23 AS build
WORKDIR /build

# Copy pom.xml and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the source code and package the application
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime Stage
FROM amazoncorretto:23

# Expose necessary arguments
ARG PROFILE=dev
ARG APP_VERSION=1.0.0

# Set working directory
WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /build/target/BookApp-${APP_VERSION}.jar app.jar

# Expose port
EXPOSE 8040

# Environment variables
ENV DB_URL=jdbc:postgresql://postgres-sql-bsn:5432/book_social_network
ENV ACTIVE_PROFILE=$PROFILE

# Run the application
CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "app.jar"]
