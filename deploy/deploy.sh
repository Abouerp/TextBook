#!/usr/bin/env bash

git fetch && \
git reset --hard origin/master

chmod +x ./mvnw && \
docker-compose up -d


echo "deploy success"