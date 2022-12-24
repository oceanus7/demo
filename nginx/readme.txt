리버스 프록시를 활용한 무중단 배포 방법

두 개의 백엔드 서버를 스위칭하는 방식으로 무중단 배포를 할 수 있다.
8081 포트와 8082 포트로 2 개의 서버 인스턴스를 띄우고, 리버스 프록시에서 바라보는 포트를 active 포트 나머지 하나를 idle 포트로 본다.
재배포시 idle 포트의 서버를 종료하고, 새로운 버전으로 재배포 후, 재시작하고, 리버스 프록시가 바라보는 서버 포트를 스위칭한 다음 nginx 를 리로드하면 된다.

1) /etc/nginx/conf.d 경로 밑에 service-url.inc 라는 파일을 만들고 vim 으로 아래와 같은 내용을 입력한다.

set $service_url http://127.0.0.1:8081;

2) nginx 의 설정 파일을 수정하여 리버스 프록시 구성을 한다.
* nginx 설정 파일 위치:
- (centos) /etc/nginx/nginx.conf
- (utuntu) /etc/nginx/site-available/default
기존의 location 부분을 주석처리하고 아래와 같이 입력한다.

include /etc/nginx/conf.d/service-url.inc;

location / {
        proxy_pass $service_url;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header Host $http_host;
        proxy_set_header X-Forwarded-Proto $scheme;
}

3) 재배포시 service-url.inc 내용을 변경한 후,
nginx 를 reload (restart 가 아니라 reload 해야 한다. restart 를 하면 일시적으로 접속이 끊어질 수 있다.) 하는 셸 스크립트를 작성하면 된다.
아래는 셸 스크립트 샘플이다.

IDLE_PORT=8082
echo “set \$service_url http://127.0.0.1:${IDLE_PORT};” | sudo tee /etc/nginx/conf.d/service-url.inc
sudo service nginx reload

4) 실제 배포 스크립트에서는 위와 같이 IDLE_PORT 를 하드코딩하면 안되고, 실제의 IDLE_PORT 를 찾는 코드를 작성해야 한다.
