# 카카오페이 사전과제 3 - 지자체 협약 지원 API 개발
## 목차
- [개발 환경](#개발-환경)
- [실행하기](#실행하기)
- [기능 요구사항](#기능-요구사항)
- [개발 제약사항](#개발-제약사항)
- [해결방법](#해결방법)

---

## 개발 환경
- 기본 환경
    - IDE: IntelliJ IDEA Ultimate
    - OS: Mac OS X
    - GIT
- Server
    - Java8
    - Spring Boot 2.2.2
    - JPA
    - H2
    - Gradle
    - Junit5


## 빌드 및 실행하기
### 터미널 환경
- Git, Java 는 설치되어 있다고 가정한다.

```
$ git clone https://github.com/CODEMCD/kakaopay-task3.git
$ cd kakaopay-task3
$ ./gradlew clean build
$ java -jar build/libs/kakaopay-task3-0.0.1-SNAPSHOT.jar
```

- 접속 Base URI: `http://localhost:8080`

## 기능 요구사항
### 필수사항
- 데이터 파일(`.csv`)에서 각 레코드를 데이터베이스에 저장하는 API 개발
- 주택금융 공급 금융기관(은행) 목록을 출력하는 API 개발
- 년도별 각 금융기관의 지원금액 합계를 출력하는 API 개발
    - 아래는 출력 예시

```
{
    “name”:”주택금융 공급현황”,
    [
        {   “year”: "2004년”,
            “total_amount”: 14145,
            “detail_amount”: {“주택도시기금”: 2143,”국민은행”: 4356,”우리은행”: 5342,...,”기타은행”: 1324},
        }
        {   “year”: "2005년”,
            “total_amount”: 23145,
            “detail_amount”: {“주택도시기금”: 1243,”국민은행”: 5336,”우리은행”: 4849,...,”기타은행”: 1093},
        }

        ...

        {   “year”: "2017년”,
            “total_amount”: 33145,
            “detail_amount”: {“주택도시기금”: 2240,”국민은행”: 4338,”우리은행”: 5131,...,”기타은행”: 1392}
        }
    ]
}
```

- 각 년도별 각 기관의 전체 지원 금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발
    - 예를들어, 2005년 ~ 2017년 중에 2010년 국민은행의 전체 지원금액(1월 ~ 12월 지원 합계)이 가장 높았다면 `{ “year": “2010” , "bank": “국민은행”}`을 결과로 출력한다.
    - 아래는 출력 예시

```
{
    “year": 2010 ,
    "bank": “국민은행”
}
```

- 전체 년도(2005 ~ 2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발
    - 예를들어, 2005년 ~ 2016년 외환은행의 평균 지원금액(매년 12달의 지원금액 평균값)을 계산하여 가장 작은 값과 큰 값을 출력한다. 소수점 이하는 반올림해서 계산한다.
    - 아래는 출력 예시

```
{
    “bank”:”외환은행”,
    "support_amount”:
    [
        { ”year”: 2008 , ”amount”: 78},
        { ”year”: 2015 , ”amount”: 1702}
    ]
}
```

### 선택사항
#### 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API 개발
- 단, 예측 알고리즘을 무엇을 써야하는지에 대한 제약은 없지만, 가장 근사치에 가까울 수록 높은 점수 부여.
- 입/출력 샘플 예제

알고리즘에 따라서 다를 수 있지만, 근사치를 제시한 것이다. 데이터는 2005년부터 2017년까지의 데이터로 2018년 금융지원 금약을 예측하면 된다.

입력

```
{
    “bank”:”국민은행”,
    “month”: 2
}
```

출력

```
{
    “bank”:”bnk3726”,
    “year”: 2018,
    “month”: 2,
    "amount”: 4850
}
```

국민은행의 기관코드가 “bnk3726”라는 가정으로, 국민은행이 2018년도 2월달에 4850억을 지원할것이라는 예측 결과이다.


## 개발 제약사항
### 필수사항
- API 기능명세에서 기술된 API 를 모두 개발한다.
- 데이터 영속성 관리 및 매핑을 위한 ORM(Object Relational Mapping)을 사용하여 각 엔티티를 정의하고 레퍼지토리를 개발한다.
    - 단, 엔티티 디자인은 지원자의 문제해결 방법에 따라 자유롭게 한다.
    - 단, 주택금융 공급기관은 독립 엔티티(기관명과 기관코드)로 디자인한다. `{“institute_name”, “institute_code”}`
- 단위 테스트 (Unit Test) 코드를 개발하여 각 기능을 검증한다.
- 모든 입/출력은 JSON 형태로 주고 받는다.
- 단, 각 API 에 HTTP Method 들(GET|POST|PUT|DEL)은 자유롭게 선택한다.

### 선택사항
- API 인증을 위해 JWT(Json Web Token)를 이용해서 Token 기반 API 인증 기능을 개발하고 각 API 호출 시에 HTTP Header 에 발급받은 토큰을 가지고 호출한다.
    - **signup 계정생성 API**: 입력으로 ID, PW 받아 내부 DB 에 계정 저장하고 토큰 생성하여 출력
        - 단, 패스워드는 인코딩하여 저장한다.
        - 단, 토큰은 특정 secret 으로 서명하여 생성한다.
    - **signin 로그인 API**: 입력으로 생성된 계정 (ID, PW)으로 로그인 요청하면 토큰을 발급한다.
    - **refresh 토큰 재발급 API**: 기존에 발급받은 토큰을 Authorization 헤더에 “Bearer Token”으로 입력 요청을 하면 토큰을 재발급한다.


## 해결방법
### 1. 데이터 파일(`.csv`)에서 각 레코드를 데이터베이스에 저장하는 API 개발
- Request

```
http://localhost:8080/file/upload
```

```
POST /file/upload HTTP/1.1
```

- Response

```json
{
    "fileName": "사전과제3.csv",
    "contentType": "text/csv",
    "size": 8889
}
```

- CSV 파일 요청을 받는 컨트롤러 구현
  - `/upload` URL 요청을 처리하는 `CsvFileUploadController` 클래스를 만듦
  -  `@RequestParam("file") MultipartFile multipartFile`을 사용하여 CSV 파일을 요청을 통해 받도록 함
    - 제약 사항에서는 요청 및 응답을 JSON으로 하도록 명시하였지만 CSV 파일 요청은 JSON이 아닌 MULTIPARTFILE 파일 형식으로 하였다. 이 이유는 여러 CSV 파일 요청을 받아줄 수 있도록 하기 위함이다.
  - 응답으로는 `FileUploadResponseDto`를 보내고 있다.
  - 파일 업로드 인수 테스트 작성
    - [참고 링크](https://dev.to/shavz/sending-multipart-form-data-using-spring-webtestclient-2gb7)
  - 데이터 확인은 테스트와 PostMan으로 직접 서버에 요청하며 확인하였다.
- CSV 파일 읽기
  - OpenCSV 라이브러리를 사용하였다.
    - 사용법이 매우 간단했다.
    - `""` 로 감싸져있는 문자열 내부 콤마(,)는 분리하지 않아 현재 데이터에 사용하기 적합하다고 생각했다.
    - [참고 링크](https://github.com/arnaudroger/SimpleFlatMapper/wiki/How-to-parse-a-csv-file-in-java)
  - `CsvFileParser.java`: CSV 파일을 헤더와 바디 부분으로 파싱하는 클래스
    - Configuration 클래스에서 빈으로 등록하여 서비스에서 주입받아 사용하였다.
    - CSV 파일에서 앞 부분과 뒷 부분이 공백인 경우 데이터를 무시하였다.
    - 현재 데이터도 뒷 부분에 `,,,,,,,`와 같은 데이터로 인해 공백이 들어간다.
    - CSV 파일 형식을 다음과 같은 제약사항으로 생각하고 만들었다.
    - `연도, 월, 기관1, 기관2, 기관3, ...`
    - 뒤에 기관이 더 추가되어도 동작하도록 만들었다.
- 엔티티
  - `HousingFinance`, `Institution`, `Fund` 세 개로 나눴다.
    - `HousingFinance`: `id`, `year`, `month`
    - `Institution`: `id`, `name`, `code`
    - `Fund`: `id`, `housingFinance_id`, `institution_id`, `amount`
  - `Fund`에서 `HousingFinance`를 `@ManyToOne`으로, `Institution`은 `@OneToOne`으로 관계를 맺었다.
    - `@OneToOne`은 `Fund`가 주 테이블이고, `Institution`이 대상 테이블이라 생각하고 단방향으로 설정하였다.
  - `Year`와 `Month`를 VO로 분리하였다.
    - 유효성 검사 로직 추가


### 2. 주택금융 공급 금융기관(은행) 목록을 출력하는 API 개발
- Request

```
http://localhost:8080/institutions
```

```
GET /institutions HTTP/1.1
```

- Response

```json
[
    {
        "name": "주택도시기금",
        "code": "public01"
    },
    {
        "name": "국민은행",
        "code": "bank01"
    },
    {
        "name": "우리은행",
        "code": "bank02"
    },
    {
        "name": "신한은행",
        "code": "bank03"
    },
    {
        "name": "한국시티은행",
        "code": "bank04"
    },
    {
        "name": "하나은행",
        "code": "bank05"
    },
    {
        "name": "농협은행/수협은행",
        "code": "bank06"
    },
    {
        "name": "외환은행",
        "code": "bank08"
    },
    {
        "name": "기타은행",
        "code": "bank99"
    }
]
```

- `Institution` 엔티티를 `findAll()`하는 것으로 해결하였다.

### 3. 년도별 각 금융기관의 지원금액 합계를 출력하는 API 개발
- Request

```
http://localhost:8080/funds/years/statistics
```

```
GET /funds/years/statistics HTTP/1.1
```

- Response

```json
[
    {
        "year": 2005,
        "totalAmount": 48016,
        "detailAmount": [
            {
                "name": "국민은행",
                "amount": 13231
            },
            {
                "name": "기타은행",
                "amount": 1376
            },
            {
                "name": "농협은행/수협은행",
                "amount": 1486
            },
            {
                "name": "신한은행",
                "amount": 1815
            },
            {
                "name": "외환은행",
                "amount": 1732
            },
            {
                "name": "우리은행",
                "amount": 2303
            },
            {
                "name": "주택도시기금",
                "amount": 22247
            },
            {
                "name": "하나은행",
                "amount": 3122
            },
            {
                "name": "한국시티은행",
                "amount": 704
            }
        ]
    },

    ...
]
```

- `JpaRepository`에서 `@Query` 어노테이션을 통해 **Native Query** 를 사용하여 해결하였다.
- Query는 { "연도", "해당 연도의 총 지원금액" } 를 반환하는 것과 { "연도", "기관 이름", "해당 연도에서 해당 기관의 총 지원금액" } 반환하는 두 가지 Query를 사용하였다.
- Query의 결과는 `List<Object[]>` 형태로 반환하며, 이를 `FundService`에서 `AnnualFundStatisticsResponseDto` 로 만들어 Controller에 리스트 형태로 반환하였다. DTO 형태는 아래와 같다.

```java
public class AnnualFundStatisticsResponseDto {
    private int year;
    private int totalAmount;
    private List<InstitutionTotalAmountDto> detailAmount = new ArrayList<>();

    // ...
}

public class InstitutionTotalAmountDto {
    private String name;
    private int amount;

    // ...
}
```

### 4. 각 년도별 각 기관의 전체 지원 금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발
- Request

```
http://localhost:8080/funds/years/maximum
```

```
GET /funds/years/maximum HTTP/1.1
```

- Response

```json
{
    "year": 2014,
    "bank": "주택도시기금"
}
```

- `JpaRepository`에서 `@Query` 어노테이션을 통해 **Native Query** 를 사용하여 해결하였다.
- 해당 Query는 Sub Query를 이용하였다. FROM 부분에서 서브 쿼리를 통해 { "연도", "기관 이름", "해당 연도에서 해당 기관의 총 지원금액" } 테이블을 만들어 해당 테이블에서 지원금액을 내림차순으로 한 후 가장 위에 있는 줄(row)을 반환하였다.

```java
public class InstitutionMaxFundResponseDto {
    private int year;
    private String bank;

    // ...
}
```

### 5. 전체 년도(2005 ~ 2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발
- Request

```
http://localhost:8080/funds/years/average/maximum-minimum?bank=외환은행
```

```
GET /funds/years/average/maximum-minimum HTTP/1.1
```

- Response

```json
[
    {
        "year": 2017,
        "amount": 0
    },
    {
        "year": 2015,
        "amount": 1701
    }
]
```

- `JpaRepository`에서 `@Query` 어노테이션을 통해 **Native Query** 를 사용하여 해결하였다.
- 해당 Query는 전체 년도에서 해당 은행의 지원 금액 평균을 구한 후 최대값과 최소값을 구하는 쿼리를 두 개로 나누었다. 이를 `JpaRepository`에서 데이터를 추출한 뒤 `FundService`에서 `ResponseDto`로 만들었다.

```java
public class AverageAmountAndYearResponseDto {
    private int year;
    private int amount;

    // ...
}
```

- 조회할 은행은 URI의 쿼리 스트링(Query String)으로 원하는 은행을 조회할 수 있도록 하였다.

### 6. 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API 개발
- Request

```
http://localhost:8080/funds/predict?bank=국민은행&month=2
```

```
GET /funds/predict HTTP/1.1
```

- Response

```json
{
    "bank": "bank01",
    "year": 2018,
    "month": 2,
    "amount": 4817
}
```

- Native Query를 사용하여 { "연도", "달", "해당 기관 지원금액" } 테이블을 조회하였다.
- `SimpleRegression` 라이브러리를 사용하여 선형회귀분석을 통해 값으르 예측하였다.
  - 좌표값 (index, amount) 형태로 데이터를 삽입하였다.
- 예측할 은행과 달은 쿼리 스트링으로 원하는 데이터를 조회할 수 있도록 하였다.
# 카카오페이 사전과제 3 - 지자체 협약 지원 API 개발
## 목차
- [개발 환경](#개발-환경)
- [실행하기](#실행하기)
- [기능 요구사항](#기능-요구사항)
- [개발 제약사항](#개발-제약사항)
- [해결방법](#해결방법)

---

## 개발 환경
- 기본 환경
    - IDE: IntelliJ IDEA Ultimate
    - OS: Mac OS X
    - GIT
- Server
    - Java8
    - Spring Boot 2.2.2
    - JPA
    - H2
    - Gradle
    - Junit5


## 실행하기


## 기능 요구사항
### 필수사항
- 데이터 파일(`.csv`)에서 각 레코드를 데이터베이스에 저장하는 API 개발
- 주택금융 공급 금융기관(은행) 목록을 출력하는 API 개발
- 년도별 각 금융기관의 지원금액 합계를 출력하는 API 개발
    - 아래는 출력 예시

```
{
    “name”:”주택금융 공급현황”,
    [
        {   “year”: "2004년”,
            “total_amount”: 14145,
            “detail_amount”: {“주택도시기금”: 2143,”국민은행”: 4356,”우리은행”: 5342,...,”기타은행”: 1324},
        }
        {   “year”: "2005년”,
            “total_amount”: 23145,
            “detail_amount”: {“주택도시기금”: 1243,”국민은행”: 5336,”우리은행”: 4849,...,”기타은행”: 1093},
        }

        ...

        {   “year”: "2017년”,
            “total_amount”: 33145,
            “detail_amount”: {“주택도시기금”: 2240,”국민은행”: 4338,”우리은행”: 5131,...,”기타은행”: 1392}
        }
    ]
}
```

- 각 년도별 각 기관의 전체 지원 금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발
    - 예를들어, 2005년 ~ 2017년 중에 2010년 국민은행의 전체 지원금액(1월 ~ 12월 지원 합계)이 가장 높았다면 `{ “year": “2010” , "bank": “국민은행”}`을 결과로 출력한다.
    - 아래는 출력 예시

```
{
    “year": 2010 ,
    "bank": “국민은행”
}
```

- 전체 년도(2005 ~ 2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발
    - 예를들어, 2005년 ~ 2016년 외환은행의 평균 지원금액(매년 12달의 지원금액 평균값)을 계산하여 가장 작은 값과 큰 값을 출력한다. 소수점 이하는 반올림해서 계산한다.
    - 아래는 출력 예시

```
{
    “bank”:”외환은행”,
    "support_amount”:
    [
        { ”year”: 2008 , ”amount”: 78},
        { ”year”: 2015 , ”amount”: 1702}
    ]
}
```

### 선택사항
#### 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API 개발
- 단, 예측 알고리즘을 무엇을 써야하는지에 대한 제약은 없지만, 가장 근사치에 가까울 수록 높은 점수 부여.
- 입/출력 샘플 예제

알고리즘에 따라서 다를 수 있지만, 근사치를 제시한 것이다. 데이터는 2005년부터 2017년까지의 데이터로 2018년 금융지원 금약을 예측하면 된다.

입력

```
{
    “bank”:”국민은행”,
    “month”: 2
}
```

출력

```
{
    “bank”:”bnk3726”,
    “year”: 2018,
    “month”: 2,
    "amount”: 4850
}
```

국민은행의 기관코드가 “bnk3726”라는 가정으로, 국민은행이 2018년도 2월달에 4850억을 지원할것이라는 예측 결과이다.


## 개발 제약사항
### 필수사항
- API 기능명세에서 기술된 API 를 모두 개발한다.
- 데이터 영속성 관리 및 매핑을 위한 ORM(Object Relational Mapping)을 사용하여 각 엔티티를 정의하고 레퍼지토리를 개발한다.
    - 단, 엔티티 디자인은 지원자의 문제해결 방법에 따라 자유롭게 한다.
    - 단, 주택금융 공급기관은 독립 엔티티(기관명과 기관코드)로 디자인한다. `{“institute_name”, “institute_code”}`
- 단위 테스트 (Unit Test) 코드를 개발하여 각 기능을 검증한다.
- 모든 입/출력은 JSON 형태로 주고 받는다.
- 단, 각 API 에 HTTP Method 들(GET|POST|PUT|DEL)은 자유롭게 선택한다.

### 선택사항
- API 인증을 위해 JWT(Json Web Token)를 이용해서 Token 기반 API 인증 기능을 개발하고 각 API 호출 시에 HTTP Header 에 발급받은 토큰을 가지고 호출한다.
    - **signup 계정생성 API**: 입력으로 ID, PW 받아 내부 DB 에 계정 저장하고 토큰 생성하여 출력
        - 단, 패스워드는 인코딩하여 저장한다.
        - 단, 토큰은 특정 secret 으로 서명하여 생성한다.
    - **signin 로그인 API**: 입력으로 생성된 계정 (ID, PW)으로 로그인 요청하면 토큰을 발급한다.
    - **refresh 토큰 재발급 API**: 기존에 발급받은 토큰을 Authorization 헤더에 “Bearer Token”으로 입력 요청을 하면 토큰을 재발급한다.


## 해결방법
### 1. 데이터 파일(`.csv`)에서 각 레코드를 데이터베이스에 저장하는 API 개발
- Request

```
http://localhost:8080/file/upload
```

```
POST /file/upload HTTP/1.1
```

- Response

```json
{
    "fileName": "사전과제3.csv",
    "contentType": "text/csv",
    "size": 8889
}
```

- CSV 파일 요청을 받는 컨트롤러 구현
  - `/upload` URL 요청을 처리하는 `CsvFileUploadController` 클래스를 만듦
  -  `@RequestParam("file") MultipartFile multipartFile`을 사용하여 CSV 파일을 요청을 통해 받도록 함
    - 제약 사항에서는 요청 및 응답을 JSON으로 하도록 명시하였지만 CSV 파일 요청은 JSON이 아닌 MULTIPARTFILE 파일 형식으로 하였다. 이 이유는 여러 CSV 파일 요청을 받아줄 수 있도록 하기 위함이다.
  - 응답으로는 `FileUploadResponseDto`를 보내고 있다.
  - 파일 업로드 인수 테스트 작성
    - [참고 링크](https://dev.to/shavz/sending-multipart-form-data-using-spring-webtestclient-2gb7)
  - 데이터 확인은 테스트와 PostMan으로 직접 서버에 요청하며 확인하였다.
- CSV 파일 읽기
  - OpenCSV 라이브러리를 사용하였다.
    - 사용법이 매우 간단했다.
    - `""` 로 감싸져있는 문자열 내부 콤마(,)는 분리하지 않아 현재 데이터에 사용하기 적합하다고 생각했다.
    - [참고 링크](https://github.com/arnaudroger/SimpleFlatMapper/wiki/How-to-parse-a-csv-file-in-java)
  - `CsvFileParser.java`: CSV 파일을 헤더와 바디 부분으로 파싱하는 클래스
    - Configuration 클래스에서 빈으로 등록하여 서비스에서 주입받아 사용하였다.
    - CSV 파일에서 앞 부분과 뒷 부분이 공백인 경우 데이터를 무시하였다.
    - 현재 데이터도 뒷 부분에 `,,,,,,,`와 같은 데이터로 인해 공백이 들어간다.
    - CSV 파일 형식을 다음과 같은 제약사항으로 생각하고 만들었다.
    - `연도, 월, 기관1, 기관2, 기관3, ...`
    - 뒤에 기관이 더 추가되어도 동작하도록 만들었다.
- 엔티티
  - `HousingFinance`, `Institution`, `Fund` 세 개로 나눴다.
    - `HousingFinance`: `id`, `year`, `month`
    - `Institution`: `id`, `name`, `code`
    - `Fund`: `id`, `housingFinance_id`, `institution_id`, `amount`
  - `Fund`에서 `HousingFinance`를 `@ManyToOne`으로, `Institution`은 `@OneToOne`으로 관계를 맺었다.
    - `@OneToOne`은 `Fund`가 주 테이블이고, `Institution`이 대상 테이블이라 생각하고 단방향으로 설정하였다.
  - `Year`와 `Month`를 VO로 분리하였다.
    - 유효성 검사 로직 추가


### 2. 주택금융 공급 금융기관(은행) 목록을 출력하는 API 개발
- Request

```
http://localhost:8080/institutions
```

```
GET /institutions HTTP/1.1
```

- Response

```json
[
    {
        "name": "주택도시기금",
        "code": "public01"
    },
    {
        "name": "국민은행",
        "code": "bank01"
    },
    {
        "name": "우리은행",
        "code": "bank02"
    },
    {
        "name": "신한은행",
        "code": "bank03"
    },
    {
        "name": "한국시티은행",
        "code": "bank04"
    },
    {
        "name": "하나은행",
        "code": "bank05"
    },
    {
        "name": "농협은행/수협은행",
        "code": "bank06"
    },
    {
        "name": "외환은행",
        "code": "bank08"
    },
    {
        "name": "기타은행",
        "code": "bank99"
    }
]
```

- `Institution` 엔티티를 `findAll()`하는 것으로 해결하였다.

### 3. 년도별 각 금융기관의 지원금액 합계를 출력하는 API 개발
- Request

```
http://localhost:8080/funds/years/statistics
```

```
GET /funds/years/statistics HTTP/1.1
```

- Response

```json
[
    {
        "year": 2005,
        "totalAmount": 48016,
        "detailAmount": [
            {
                "name": "국민은행",
                "amount": 13231
            },
            {
                "name": "기타은행",
                "amount": 1376
            },
            {
                "name": "농협은행/수협은행",
                "amount": 1486
            },
            {
                "name": "신한은행",
                "amount": 1815
            },
            {
                "name": "외환은행",
                "amount": 1732
            },
            {
                "name": "우리은행",
                "amount": 2303
            },
            {
                "name": "주택도시기금",
                "amount": 22247
            },
            {
                "name": "하나은행",
                "amount": 3122
            },
            {
                "name": "한국시티은행",
                "amount": 704
            }
        ]
    },

    ...
]
```

- `JpaRepository`에서 `@Query` 어노테이션을 통해 **Native Query** 를 사용하여 해결하였다.
- Query는 { "연도", "해당 연도의 총 지원금액" } 를 반환하는 것과 { "연도", "기관 이름", "해당 연도에서 해당 기관의 총 지원금액" } 반환하는 두 가지 Query를 사용하였다.
- Query의 결과는 `List<Object[]>` 형태로 반환하며, 이를 `FundService`에서 `AnnualFundStatisticsResponseDto` 로 만들어 Controller에 리스트 형태로 반환하였다. DTO 형태는 아래와 같다.

```java
public class AnnualFundStatisticsResponseDto {
    private int year;
    private int totalAmount;
    private List<InstitutionTotalAmountDto> detailAmount = new ArrayList<>();

    // ...
}

public class InstitutionTotalAmountDto {
    private String name;
    private int amount;

    // ...
}
```

### 4. 각 년도별 각 기관의 전체 지원 금액 중에서 가장 큰 금액의 기관명을 출력하는 API 개발
- Request

```
http://localhost:8080/funds/years/maximum
```

```
GET /funds/years/maximum HTTP/1.1
```

- Response

```json
{
    "year": 2014,
    "bank": "주택도시기금"
}
```

- `JpaRepository`에서 `@Query` 어노테이션을 통해 **Native Query** 를 사용하여 해결하였다.
- 해당 Query는 Sub Query를 이용하였다. FROM 부분에서 서브 쿼리를 통해 { "연도", "기관 이름", "해당 연도에서 해당 기관의 총 지원금액" } 테이블을 만들어 해당 테이블에서 지원금액을 내림차순으로 한 후 가장 위에 있는 줄(row)을 반환하였다.

```java
public class InstitutionMaxFundResponseDto {
    private int year;
    private String bank;

    // ...
}
```

### 5. 전체 년도(2005 ~ 2016)에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API 개발
- Request

```
http://localhost:8080/funds/years/average/maximum-minimum?bank=외환은행
```

```
GET /funds/years/average/maximum-minimum HTTP/1.1
```

- Response

```json
[
    {
        "year": 2017,
        "amount": 0
    },
    {
        "year": 2015,
        "amount": 1701
    }
]
```

- `JpaRepository`에서 `@Query` 어노테이션을 통해 **Native Query** 를 사용하여 해결하였다.
- 해당 Query는 전체 년도에서 해당 은행의 지원 금액 평균을 구한 후 최대값과 최소값을 구하는 쿼리를 두 개로 나누었다. 이를 `JpaRepository`에서 데이터를 추출한 뒤 `FundService`에서 `ResponseDto`로 만들었다.

```java
public class AverageAmountAndYearResponseDto {
    private int year;
    private int amount;

    // ...
}
```

- 조회할 은행은 URI의 쿼리 스트링(Query String)으로 원하는 은행을 조회할 수 있도록 하였다.

### 6. 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API 개발
- Request

```
http://localhost:8080/funds/predict?bank=국민은행&month=2
```

```
GET /funds/predict HTTP/1.1
```

- Response

```json
{
    "bank": "bank01",
    "year": 2018,
    "month": 2,
    "amount": 4817
}
```

- Native Query를 사용하여 { "연도", "달", "해당 기관 지원금액" } 테이블을 조회하였다.
- `SimpleRegression` 라이브러리를 사용하여 선형회귀분석을 통해 값으르 예측하였다.
  - 좌표값 (index, amount) 형태로 데이터를 삽입하였다.
- 예측할 은행과 달은 쿼리 스트링으로 원하는 데이터를 조회할 수 있도록 하였다.
