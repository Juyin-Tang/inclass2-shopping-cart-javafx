FROM bellsoft/liberica-runtime-container:jre-21-slim-musl
COPY target/shopping-cart.jar /app/app.jar
WORKDIR /app
CMD ["java", "-jar", "app.jar"]