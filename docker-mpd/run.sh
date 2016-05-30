#!/bin/bash
set -e
NAME=mock-mpd

echo "Build and Run the Mock Mpd Docker container"
echo "Stop existing container if any"
docker stop $NAME | true
docker rm $NAME | true
rm -rf music ; cp -r ../build/music ./
echo "Build the mock mpd docker image"
docker build -t $NAME .
echo "Run the mock mpd docker image"
docker run -d -p 6600:6600 --name $NAME $NAME
echo "Mock Mpd docker build and run OK"
