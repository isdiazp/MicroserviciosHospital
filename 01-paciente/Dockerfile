FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY target/paciente-1.0.0.jar app.jar
# No copiamos el JAR aquí, dejaremos que Docker lo maneje mediante volúmenes
EXPOSE 8081
# Comando para ejecutar la aplicación en modo desarrollo (usando el wrapper de Maven)

ENTRYPOINT ["java","-jar","app.jar"]
#CMD ["./mvnw", "spring-boot:run"]