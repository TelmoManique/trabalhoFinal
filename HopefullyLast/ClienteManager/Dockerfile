FROM openjdk:18
MAINTAINER Telmo Manique
COPY target/ClienteManager-0.0.1-SNAPSHOT.jar cliente_manager.jar
ENTRYPOINT ["java","-jar","cliente_manager.jar"]

ENTRYPOINT exec java $JAVA_OPTS -jar cliente_manager.jar