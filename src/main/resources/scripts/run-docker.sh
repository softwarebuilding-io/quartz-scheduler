#!/bin/bash

echo 'Starting docker Compose'

docker-compose -f src/main/resources/docker/docker-compose.yaml -p "catalogue-management" up -d