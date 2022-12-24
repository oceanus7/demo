* jenkins 를 사용하여 CI 를 구축할 때, github 에 코드 푸시를 하면 자동으로 빌드 이벤트가 발생하도록 하려면, github 에 webhook 설정을 해야 한다.
 또한 jenkins 서버에 80 포트가 열려있어야 한다. (!중요!)

* jenkins 서버에 리버스 프록시를 설정했을 경우, jenkins 시스템 설정에서 jenkins URL 도 변경해 주어야 한다.

* 리버스 프록시를 활용한 무중단 배포 시스템을 구성할 수 있다. 일반적으로 리버스 프록시는 apache 나 nginx 를 사용하여 구축할 수 있다. 여기에서는 nginx 를 사용하여 구축하였다.

* 재배포 시, stop.sh -> start.sh -> health.sh 순으로 실행된다.

* stop.sh
Idle 포트에서 실행 중인 인스턴스를 종료한다. Idle 포트는 profile.sh 의 find_idle_port() 함수를 호출하여 찾는다.

* start.sh
새로 배포한 jar 파일을 지정된 위치로 복사한 다음, Idle Profile 로 실행시킨다. Idle Profile 은 profile.sh 의 find_idle_profile() 함수를 호출하여 찾을 수 있다.

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)
-> 배포가 반복되면 jar 파일이 여러 개 존재하게 된다. tail -n 으로 가장 최신의 파일명을 얻는다.

nohup java -jar -Dspring.profiles.active=$IDLE_PROFILE $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
-> nohup ... & 는 애플리케이션을 데몬 모드 + 백그라운드로 실행시킨다. 이렇게 해야 쉡 접속 세션이 끊어져도 백그라운드에서 계속 실행이 된다.
-> 2>&1 이 의미하는 것은 2(stderr) 출력을 1(stdout) 출력에 같이 포함시키란 뜻이다.
-> > (리다이렉션) 기호를 사용해 표준 출력(stdout)을 $REPOSITORY/nohup.out 로 하도록 지정한다. 본 스크립트가 코드 디플로이에서 실행되므로 이렇게 하지 않으면, 표준 출력이 코드 디플로이 로그로 출력된다.

* health.sh
Idle 포트에 새롭게 배포/실행된 애플리케이션이 정상 응답을 확인한 다음, 리버스 프로시의 연결 포트를 전환한다. 리버스 프록시 연결 포트 전환은 switch.sh 의 switch_proxy() 함수를 사용한다.
리버스 프록시의 연결 포트 전환 방법은 다음과 같다.
service-url.inc 라는 파일에 $service_url 이라는 변수를 써넣는다. 값은 전환할 Idle 포트를 향한 주소이다.
nginx 의 conf 파일에서 service-url.inc 파일을 include 하고, proxy_pass 설정을 $service_url 로 지정한다.
service-url.inc 파일에 새로운 값을 써 넣고, nginx 를 reload 하면 proxy_pass 설정이 변경되어, 리버스 프록시 연결이 전환된다.

* 리버스 프록시를 활용한 무중단 배포
서버 인스턴스를 항상 2개 띄워 놓아야 한다는 단점이 있으나 운영 서버 다운으로 인한 서비스 중단을 대비하기 위해서도 이중화는 필요한 만큼 큰 단점은 아니다.

* 빌드 시스템으로 travis ci 가 간편한 설정에 안정적인 운영이 가능한 장점이 있으나 유료 서비스인데다가 그 가격도 꽤 비싸다. 그냥 jenkins 를 구축하여 쓰자.

* github + jenkins 또는 github + jenkins + AWS code deploy 로 배포 시스템을 구성한다. jenkins 는 빌드와 배포를 겸할 수 있다.
단, 배포 파일 저장을 별도로 해야 하는데, S3 를 사용하여 배포 파일 백업을 할 거라면 code deploy 를 같이 사용하는 것이 낫다.

* DevOps 실현을 위한 전체 빌드/배포 시스템 구성은 다음과 같다.

단일 코드 접점(Code Repository) : github
자동 빌드 또는 원 클릭 빌드(CI) : jenkins + gradle
테스트 자동 실행(Test Automation): gradle + JUnit
배포 파일 백업 저장(Backup) : AWS S3
배포(CD) : AWS Code Deploy
무중단 배포(Zero-downtime Deployment) : nginx(리버스 프록시)
