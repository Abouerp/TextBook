#!/usr/bin/env bash

git fetch && \
git reset --hard origin/master && \
chmod +x ./mvnw && \
./mvnw clean install package -DskipTests=true && \
cp api/target/api-0.0.1.jar /home/face/api.jar && \
systemctl restart face
