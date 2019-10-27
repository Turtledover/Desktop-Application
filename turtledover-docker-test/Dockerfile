FROM ubuntu

RUN apt-get update && apt-get install openjdk-8-jdk -y

COPY util/desk_app.jar /util/desk_app.jar
COPY util/run.sh /util/run.sh

RUN chmod +x /util/run.sh
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

WORKDIR /util
CMD ["./run.sh"]
