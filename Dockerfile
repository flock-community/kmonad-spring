FROM openjdk:12-alpine

WORKDIR /app
COPY target/kmonad-spring-*.jar /app
EXPOSE 8080

CMD java -jar kmonad-spring-*.jar
