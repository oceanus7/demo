* jenkins 를 사용하여 CI 를 구축할 때, github 에 코드 푸시를 하면 자동으로 빌드 이벤트가 발생하도록 하려면, github 에 webhook 설정을 해야 한다.
 또한 jenkins 서버에 80 포트가 열려있어야 한다. (!중요!)

* jenkins 서버에 리버스 프록시를 설정했을 경우, jenkins 시스템 설정에서 jenkins URL 도 변경해 주어야 한다.
