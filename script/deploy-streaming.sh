#!/usr/bin/env bash

export JOB_NAME="coin-info-streaming"
export SERVICE_PATH="/coin-streaming/v1"
export IMAGE_TAG="1.0.1"

# deploy module streaming
echo "[Deployment] Deploying coin-info-streaming application"
cd ./streaming/k8s && envsubst < deploy.sh > k8s-deploy.sh && bash k8s-deploy.sh
rm k8s-deploy.sh && rm k8s-config.yml && rm k8s-main.yml

echo "[Deployment] Streaming application deployment completed"
