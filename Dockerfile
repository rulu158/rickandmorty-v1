FROM eclipse-temurin:17-jdk-focal
ADD target/rickandmorty-1.0.0.jar rickandmorty-1.0.0.jar
EXPOSE 9950
ENTRYPOINT ["java", "-jar", "rickandmorty-1.0.0.jar"]
