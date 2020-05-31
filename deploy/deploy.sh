#!/usr/bin/env bash

git fetch && \
git reset --hard origin/master

chmod +x ./mvnw && \
./mvnw clean compile jib:dockerBuild
docker rmi `docker images | grep  "<none>" | awk '{print $3}'`

echo "deploy success"