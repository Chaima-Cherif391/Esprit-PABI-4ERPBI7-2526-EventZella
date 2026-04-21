FROM apache/airflow:2.8.1

USER root

RUN apt-get update && \
    apt-get install -y default-jdk && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENV JAVA_HOME=/usr/lib/jvm/default-java
ENV PATH="${JAVA_HOME}/bin:${PATH}"

RUN chmod -R +x /opt/airflow/talend_jobs/ 2>/dev/null || true

USER airflow