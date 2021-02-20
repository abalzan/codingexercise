FROM openjdk:11-jre-slim as builder

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

ADD ["target/codingexercise-0.0.1-SNAPSHOT.jar", "codingexercise-0.0.1-SNAPSHOT.jar"]
RUN sh -c 'touch /"codingexercise-0.0.1-SNAPSHOT.jar"'

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /codingexercise-0.0.1-SNAPSHOT.jar" ]

EXPOSE 8080