# Getting Started
- 개발 프레임워크, 테이블 설계, 문제해결 전략, 빌드 및 실행 방법을 기술하세요.


## Environment
```
 Framwork : SpringBoot 2.3.11.RELEASE
 Build : Gradle 6.9
 Language : Java11 OpneJDK11
 Encoding : UTF-8
```

## Build & Run
```
 ./gradlew wrapper --gradle-version={version}
 ./gradlew -v
 ./gradlew clean build -xtest
 ./gradlew clean bootRun --args='--spring.profiles.active=h2'
```
## H2DB 접속
- http://localhost:8080/h2-console/login.do
- testuser / testuser

## Swagger 접속
- http://localhost:8080/swagger-ui.html

## API

1. 결제 API
```
(호출예시)
POST - http://localhost:8080/v1/payments/approve
{
    "cardNumber" : "1234567890123456",
    "cardExpireDate" : "0225",
    "cardCvc" : "110",
    "cardMonthlyInstallment" : "0",
    "paymentPrice" : 20000,
    "paymentVat" : 909
}

(응답예시)
{
    "status": 200,
    "message": "OK",
    "data": {
        "paymentSerialNumber": "hUfFrkZWXfLKToGGFjeN",
        "datayPayload": " 416PAYMENT   hUfFrkZWXfLKToGGFjeN1234567890123456    000225110     200000000000909                    dXzNDNxckOrb7uz2ON0AAInkf0+ztESwzOOm+pz7Kpc=                                                                                                                                                                                                                                                                                                               "
    }
}
```

2. 결제취소 API
```
(호출예시)
POST - http://localhost:8080/v1/payments/cancel
{
    "paymentSerialNumber" : "w26BUPkGYMgdSpaYQzxh",
    "cancelPrice" : 10000,
    "cancelVat" : 909
}

(응답예시)
{
    "status": 200,
    "message": "OK",
    "data": {
        "paymentSerialNumber": "K4auw37ONfH2iv7uIX0z",
        "datayPayload": " 416CANCEL    K4auw37ONfH2iv7uIX0z1234567890123456    000225110     100000000000909K4auw37ONfH2iv7uIX0zdXzNDNxckOrb7uz2ON0AAInkf0+ztESwzOOm+pz7Kpc=                                                                                                                                                                                                                                                                                                               "
    }
}
```

3. 데이터조회API
```
(호출예시)
GET - http://localhost:8080/v1/payments?id=oqeCpwiB5jcSNphm457M

(응답예시)
{
    "status": 200,
    "message": "OK",
    "data": {
        "dataType": "PAYMENT",
        "paymentSerialNumber": "oqeCpwiB5jcSNphm457M",
        "cardNumber": "1234567890123456",
        "cardExpireDate": "0225",
        "cardCvc": "110",
        "price": 11000,
        "vat": 1000
    }
}
```

##  테이블 설계
1. 카드사 통신 내용 저장테이블(CARD_COMPANY)

2. 카드사 통신 후 승인내용 저장테이블(PAYMENT_APPROVAL)

3. 카드사 통신 후 취소내용 저장테이블(PAYMENT_CANCEL)


## 문제해결 전략
- 기본적으로 PDF내의 TestCase의 문제해결 중심으로 진행했습니다.
- 취소 시 승인값을 차감하면서 하는 건지 아닌지 헷갈렸는데 결제승인은 갱신하지 않으면서 진행했습니다.

