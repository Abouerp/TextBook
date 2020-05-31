#!/usr/bin/env bash

git fetch && \
git reset --hard origin/master

chmod +x ./mvn && \
./mvn clean jibDockerBuild
docker images | awk '$1 == "<none>" || $2 == "<none>" {print $3}' | xargs docker rmi

echo "deploy success"