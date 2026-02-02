# Etapa 1: Build del proyecto
FROM maven:3.9.2-eclipse-temurin-17 AS build

# Directorio de trabajo
WORKDIR /app

# Copiamos archivos Maven para aprovechar cache
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

# Descargamos dependencias (cache)
RUN ./mvnw dependency:go-offline -B || mvn dependency:go-offline -B

# Copiamos el código fuente
COPY src ./src

# Build del JAR (sin tests para acelerar)
RUN ./mvnw clean package -DskipTests || mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiamos el JAR compilado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Puerto que va a exponer la app
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java","-jar","app.jar"]
