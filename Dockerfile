FROM openjdk:8-jre
VOLUME /tmp
ADD target/*.jar app.jar
ENTRYPOINT ["java","-Xmx200m","-jar","app.jar"]