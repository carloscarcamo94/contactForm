# ETAPA 1: Construcción (Descarga dependencias y compila el código)
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app

# Copiamos el wrapper de Maven y el archivo de dependencias
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Le damos permisos de ejecución al wrapper (necesario para Linux/Render)
RUN chmod +x mvnw

# Descargamos las dependencias
RUN ./mvnw dependency:go-offline

# Copiamos el código fuente y compilamos ignorando los tests
COPY src ./src
RUN ./mvnw clean package -DskipTests

# ETAPA 2: Ejecución (Crea un contenedor ligero solo con lo necesario para correr)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiamos el archivo .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]