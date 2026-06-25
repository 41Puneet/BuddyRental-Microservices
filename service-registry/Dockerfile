FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8761

CMD ["java","-jar","target/service-registry-0.0.1-SNAPSHOT.jar"]