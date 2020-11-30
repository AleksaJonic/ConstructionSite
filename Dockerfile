FROM maven:3.6.3-jdk-14 AS BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package
FROM openjdk:13-alpine
WORKDIR /project
COPY --from=BUILD /build/target/constructions-0.0.1-SNAPSHOT.jar /project/
CMD ["java", "-jar", "constructions-0.0.1-SNAPSHOT.jar"]
