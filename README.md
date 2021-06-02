# Getting Started
- 개발 프레임워크, 테이블 설계, 문제해결 전략, 빌드 및 실행 방법을 기술하세요.


## Environment
```
 Framwork : SpringBoot 2.3.11.RELEASE
 Build : Gradle 6.9
 Language : Java11 OpneJDK11
 Encoding : UTF-8
```

##  테이블 설계


## 문제해결 전략
 카드사 전송은 평문이고, 암/복호화 대상 데이터는  DB에 저장하는 카드번호, 유호기간, cvc 으로 이해했습니다.
 

## Build & Run
```
 ./gradlew wrapper --gradle-version={version}
 ./gradlew -v
 ./gradlew clean build -xtest
 ./gradlew clean bootRun --args='--spring.profiles.active=h2'
```

