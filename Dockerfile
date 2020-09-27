FROM openjdk:8-jdk-alpine

COPY target/*.jar /home/gateway-service.jar

EXPOSE 8443

CMD java -jar /home/gateway-service.jar