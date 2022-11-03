ARG SPARK_IMAGE=us-central1-docker.pkg//spark-poc/spark:v3.0.0
FROM ${SPARK_IMAGE}

USER ${spark_uid}

WORKDIR /app

COPY target/firstscalaspark-1.0-SNAPSHOT-jar-with-dependencies.jar .

