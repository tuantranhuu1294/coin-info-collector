#!/usr/bin/env bash
if [[ -z $IMAGE_TAG ]]; then
  echo "IMAGE_TAG is empty"
  exit 1
fi

export DATE=$(date +%s)

envsubst < config.yml > k8s-config.yml && kubectl apply -f k8s-config.yml
envsubst < main.yml > k8s-main.yml && kubectl apply -f k8s-main.yml

if [[ $? != 0 ]]; then exit 1; fi

kubectl rollout status deployments/$JOB_NAME --timeout 300s

if [[ $? != 0 ]]; then
    kubectl logs $(kubectl get pods --sort-by=.metadata.creationTimestamp | grep "$JOB_NAME" | awk '{print $1}' | tac | head -1 ) --tail=100 && exit 1;
fi
