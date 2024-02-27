#!/bin/bash

echo 'Clearing docker containers, images, volumes and networks'

# Stop running containers
CONTAINERS=$(docker ps -q)
if [ -n "$CONTAINERS" ]; then
    echo "Stopping running containers..."
    docker stop $CONTAINERS
else
    echo "No running containers to stop."
fi

# Remove all containers
ALL_CONTAINERS=$(docker ps -aq)
if [ -n "$ALL_CONTAINERS" ]; then
    echo "Removing all containers..."
    docker rm $ALL_CONTAINERS
else
    echo "No containers to remove."
fi

# Remove all images
IMAGES=$(docker images -q)
if [ -n "$IMAGES" ]; then
    echo "Removing all images..."
    docker rmi $IMAGES
else
    echo "No images to remove."
fi

# Removing volumes
echo "Removing volumes..."
docker volume rm $(docker volume ls -q)

# Prune networks
echo "Pruning networks..."
docker network prune -f
