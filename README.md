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

## 문제해결 전략
- 기본적으로 PDF내의 TestCase의 문제해결 중심으로 진행했습니다.
- 이벤트 발생 시 카드사와 통신을 한 후 처리가 된다는 가정으로 진행했습니다.
- 취소 시 승인값을 차감하면서 하는 건지 아닌지 헷갈렸는데 결제승인은 갱신하지 않으면서 진행했습니다.


##  테이블 설계
- 카드사 통신이 DB에 저장하는 것으로 간소화하여 아래와 같이 작업했습니다.
1. 카드사 통신 내용 저장테이블(CARD_COMPANY)
2. 카드사 통신 후 승인내용 저장테이블(PAYMENT_APPROVAL)
3. 카드사 통신 후 취소내용 저장테이블(PAYMENT_CANCEL)

## 회고
- 트래픽이 많은 회사들은 주문 단계에서부터 카프카나 래빗엠큐를 사용하기도 한다.
- 주문과 같은 매개체없이 결제를 구현해 보려고 하니 여기에 어떻게 적용될지 생각해보기도 했다.
- 구현체는 간단한 데모이지만 보통 외부사통신시 커넥션 몇회 재시도나 실패시 처리를 구현했어야 했을 것이다.
- 미구현된 기능, 취소 시 JPA version을 활용한 락을 보통 사용했을 때 승인 건을 조회 후 version을 받아서 취소를 넘야 하는데 개인사정으로 늦게 시작한게 아쉽다. ㅠㅠ
...
