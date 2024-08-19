FROM docker.io/openjdk:21-jdk

ENV APPLICATION="jenkins-jcasc-maven-example"
COPY ./target/${APPLICATION}.jar /application/${APPLICATION}/${APPLICATION}.jar
ENTRYPOINT java -jar ${JAVA_OPTS} /application/${APPLICATION}/${APPLICATION}.jar