# Etapa de construcción
FROM maven:3.8.4-jdk-17 AS build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo pom.xml y descargar las dependencias
COPY pom.xml /app/
RUN mvn dependency:go-offline -B

# Copiar el resto del código fuente y construir la aplicación
COPY src /app/src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR desde la etapa de construcción
COPY --from=build /app/target/pogotracker-0.0.1.jar /app/pogotracker-0.0.1.jar

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "pogotracker-0.0.1.jar"]
