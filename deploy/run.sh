#!/bin/bash

echo "Pulling the latest images..."
docker compose pull app

echo "Starting backend app..."
docker compose up -d app

docker ps

echo "App seems up and running. Enjoy!"


