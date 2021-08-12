#!/usr/bin/env bash

export IMAGE_TAG="1.0.1"

echo "[CloudBuild] Docker build and push to google cloud build"
envsubst < cloudbuild.yaml > cloudbuild-final.yaml && gcloud builds submit --config cloudbuild-final.yaml .
rm cloudbuild-final.yaml
