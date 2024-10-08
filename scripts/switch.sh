#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
  IDLE_PORT=$(find_idle_port)

  echo "> 전환할 포트: $IDLE_PORT"

  echo "> 리버스 프록시 연결 포트 전환"

  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

  echo "> 리버스 프록시 Reload"

  sudo systemctl reload nginx
}
