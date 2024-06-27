FROM alpine/java:21-jdk
COPY target/order-service-0.0.1-SNAPSHOT.jar order-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/order-service-0.0.1-SNAPSHOT.jar"]