#!/bin/bash

## Check if a tag argument is provided
#if [ -z "$1" ]; then
#  echo "Usage: $0 <tag>"
#  exit 1
#fi
#
#TAG=$1
#
## Build and push the Docker images
#docker build -t tyrael122/order-service:$TAG ./order-service/
#docker build -t tyrael122/user-service:$TAG ./user-service/
#
#docker push tyrael122/order-service:$TAG
#docker push tyrael122/user-service:$TAG
#
## Update deployment.yaml with the new tag
#sed -i "s/tag: .*/tag: $TAG/" deployment.yaml
#
## Apply the updated deployment.yaml

mvn -f ./order-service/pom.xml clean package &
mvn -f ./user-service/pom.xml clean package &
mvn -f ./portfolio-service/pom.xml clean package &