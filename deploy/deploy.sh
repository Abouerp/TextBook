#!/usr/bin/env bash

git fetch && \
git reset --hard origin/master

chmod +x ./mvn && \
mvn clean compile jibDockerBuild -X

echo "deploy success"