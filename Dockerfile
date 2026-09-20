# --- Build stage ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Cache dependencies first
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

# Now copy source and build
COPY src ./src
RUN mvn -q -B clean package -DskipTests

# --- Runtime stage ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app/target/landing-site.jar app.jar

# Railway sets $PORT at runtime; application.properties reads it
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
