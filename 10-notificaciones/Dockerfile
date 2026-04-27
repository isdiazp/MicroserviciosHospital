FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# No copiamos el JAR aquí, dejaremos que Docker lo maneje mediante volúmenes
EXPOSE 8080
# Comando para ejecutar la aplicación en modo desarrollo (usando el wrapper de Maven)
CMD ["./mvnw", "spring-boot:run"]