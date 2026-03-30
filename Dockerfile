FROM eclipse-temurin:17-jre
WORKDIR /app
COPY target/shopping-cart.jar /app/shopping-cart.jar
ENTRYPOINT ["java", "-jar", "shopping-cart.jar"]