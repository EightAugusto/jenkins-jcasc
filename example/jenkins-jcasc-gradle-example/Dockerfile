FROM docker.io/openjdk:21-jdk

ENV APPLICATION="jenkins-jcasc-gradle-example"
COPY ./build/libs/${APPLICATION}.jar /application/${APPLICATION}/${APPLICATION}.jar
ENTRYPOINT java -jar ${JAVA_OPTS} /application/${APPLICATION}/${APPLICATION}.jar