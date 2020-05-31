#!/usr/bin/env bash

git fetch && \
git reset --hard origin/master

chmod +x ./mvnw && \
./mvnw clean compile jib:dockerBuild

echo "deploy success"