FROM ubuntu

RUN apt-get update
RUN apt-get install openjdk-8-jdk python3-pip -y

COPY target/desk_app-1.0-SNAPSHOT-jar-with-dependencies.jar /util/desk_app.jar
COPY ./run.sh /util/run.sh

RUN chmod +x /util/run.sh
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

WORKDIR /util
CMD ["./run.sh"]
