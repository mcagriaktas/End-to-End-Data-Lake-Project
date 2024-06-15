FROM python:latest

# Install Java
RUN mkdir -p /usr/share/man/man1 && \
    apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV PATH=$PATH:$JAVA_HOME/bin
RUN echo $JAVA_HOME

# Install dependencies
RUN apt-get update -y && \
    apt-get install -y libzbar-dev bash gcc git libc-dev curl wget vim nano iputils-ping telnet openssh-client net-tools \
    man unzip vim-tiny bc openssh-server thrift-compiler sudo build-essential dos2unix && \
    apt-get autoremove -y && \
    apt-get clean

# Install Spark
RUN curl -o spark-3.5.1-bin-hadoop3.tgz https://dlcdn.apache.org/spark/spark-3.5.1/spark-3.5.1-bin-hadoop3.tgz && \
    tar -xzvf spark-3.5.1-bin-hadoop3.tgz && \
    mv spark-3.5.1-bin-hadoop3 /opt/spark && \
    rm -rf spark-3.5.1-bin-hadoop3.tgz

ENV SPARK_HOME=/opt/spark
ENV PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin
ENV PYTHONPATH=$SPARK_HOME/python3:$SPARK_HOME/python3/lib/py4j-0.10.7-src.zip:$PYTHONPATH

RUN chmod -R 777 /opt/spark
RUN mkdir -p /opt/spark/history
RUN chmod 777 /opt/spark/history

# Copy Spark configurations
COPY spark-defaults.conf /opt/spark/conf/spark-defaults.conf
RUN chmod 777 /opt/spark/conf/spark-defaults.conf

# Create directories for submit files
RUN mkdir -p /opt/submitfiles && chmod 777 /opt/submitfiles

# Install Python dependencies
COPY requirements.txt requirements.txt
RUN pip install --upgrade pip && pip install --no-cache-dir -r requirements.txt

ADD spark-worker.sh /
RUN chmod +x /spark-worker.sh

# SSH to spark-master
USER root
RUN apt-get update && apt-get install -y openssh-client && apt-get clean

RUN useradd -m -u 1001 -s /bin/bash sparkworker_user
RUN chown -R sparkworker_user:sparkworker_user /opt

RUN chmod 777 /opt

USER 1001

# Expose necessary ports
EXPOSE 22 7077 8080 4040 18080

ENTRYPOINT ["/bin/bash", "-c", "/opt/spark/sbin/start-history-server.sh && /spark-worker.sh"]

