#!/usr/bin/env bash

git fetch && \
git reset --hard origin/master

chmod +x ./mvn && \
./mvn compile jib:dockerBuild


echo "deploy success"