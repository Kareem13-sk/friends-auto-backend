FROM eclipse-temurin:11

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean install

EXPOSE 8080

CMD ["java", "-jar", "target/friends-auto-mobile-0.0.1-SNAPSHOT.jar"]