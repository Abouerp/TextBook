#!/usr/bin/env bash

git fetch && \
git reset --hard origin/master

chmod +x ./mvnw && \
./mvnw compile jib:dockerBuild


echo "deploy success"