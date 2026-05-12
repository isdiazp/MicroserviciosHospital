FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

EXPOSE 8081
# Comando para ejecutar la aplicación en modo desarrollo (usando el wrapper de Maven)

ENTRYPOINT ["java","-jar","app.jar"]
#CMD ["./mvnw", "spring-boot:run"]