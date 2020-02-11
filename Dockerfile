FROM ubuntu

RUN apt-get clean
RUN apt-get update
RUN apt-get install openjdk-8-jdk python3-pip -y
Run apt-get -y dist-upgrade
RUN apt-get install openssh-server openssh-client zip -y
RUN pip3 install psutil requests

COPY target/desk_app-1.0-SNAPSHOT-jar-with-dependencies.jar /util/desk_app.jar
COPY ./run.sh /util/run.sh
COPY ./config.cfg /util/config.cfg

COPY ./src/main/resources/init_worker.py /util/init_worker.py
COPY ./src/main/resources/machine.txt /util/machine.txt
COPY ./src/main/resources/auto_add_or_remove_machine.py /util/auto_add_or_remove_machine.py

RUN chmod +x /util/run.sh
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

WORKDIR /util

RUN apt-get update