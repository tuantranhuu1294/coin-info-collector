#!/usr/bin/env bash

export JOB_NAME="coin-info-crawler"
export SERVICE_PATH="/coin-crawler/v1"
export IMAGE_TAG="1.0.1"

# deploy module crawler
echo "[Deployment] Deploying coin-info-crawler application"
cd ./crawler/k8s && envsubst < deploy.sh > k8s-deploy.sh && bash k8s-deploy.sh
rm k8s-deploy.sh && rm k8s-config.yml && rm k8s-main.yml

echo "[Deployment] Coin-crawler application deployment completed"
