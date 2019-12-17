# 카카오페이 사전과제 3 - 지자체 협약 지원 API 개발
## 목차
- [개발 환경](#개발-환경)
- [빌드 및 실행하기](#빌드-및-실행하기)
- [기능 요구사항](#기능-요구사항)
- [개발 제약사항](#개발-제약사항)
- [해결방법](#해결방법)
- [코드 리뷰](#코드-리뷰)

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
    - 유효성 검사 로직 추가


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


## 코드 리뷰
- [ ] H2 데이터베이스를 사용한 이유
  - H2: `간단한 개념 찾아서 작성해볼 것`
  - 지금 구현한 것은 평가를 위한 과제이다. 과제 특성상 요구 사항이 정확히 동작하는지가 중요하다. 동작 확인을 위한 과정을 최소로 하고 싶었다. 그래서 in-memory-database인 H2 데이터베이스를 프로덕션 코드에서 사용하게 되었다.
- 엔티티를 `HousingFinance`, `Institution`, `Fund`로 나눈 이유
  - 관계형 데이터베이스를 설계할 때는 데이터의 중복을 최대한 줄이는 것이라고 배웠다. 그래서 기금에서 중복되는 기관 이름과 연도, 월을 다른 테이블로 분리하였다.
  - `HousingFinance`의 컬럼으로 Year와 Month 데이터가 저장되어 있는데 이름을 `HousingFinance`로 설정하는 것은 잘못되었다고 생각한다. `FundTime`과 같은 해당 기관의 시간을 나타내는 의미의 이름으로 변경했으면 좋았을 것이다.
- 해당 과제 애플리케이션은 CSV 파일을 파싱하는 기능이 필요한데 이러한 주요한 기능은 유틸보다는 도메인에 가깝다고 생각할 수도 있다.
- [ ]기본키를 **AutoIncrement** 를 사용하는 것이 좋은 이유
  - AutoIncrement는 정수형 타입으로 문자열을 사용하는 것보다는 계산이 빠르다.
  - AutoIncrement가 아니면 `INSERT` 연산 때마다 테이블을 항상 새로 정렬해야 하고 이에 따라 B Tree 역시 새로 만들어야 한다. 이러한 계산 오버헤드가 발생하여 성능상 비효율적이다.
- [ ] 유틸이나 외부 라이브러리를 사용할 때 방법
  - 빈(Bean)으로 등록하기
    - 장점: 스프링 프레임워크에서 스프링을 적극적으로 사용할 수 있다. 좀더 객체지향적이고 스태틱으로 선언하는 것보다 멀티쓰레드에서 안전한다.
    - 단점: 의존 주입이 많아진다.
  - Static으로 선언하기
    - 장점: 간단하게 만들고 사용할 수 있다. 원자적인 계산 및 로직은 스태틱 사용을 권장하는 편이다.
    - 단점: 객체지향에 위배된다. 수정이 필요하면 일일이 해당 스태틱 클래스를 찾아야 하므로 비효율적이다.
- 쿼리
  - 네트워크 비용과 데이터베이스의 연산 비용을 비교할 수 있어야 한다.
  - 데이터베이스 연산 비용과 서버 연산 비용을 비교할 수 있어야 한다.
    - 서버가 데이터베이스보다 확장성이 좋으므로 서버에서 연산을 부담하는 것을 추천한다. 하지만 이 역시 상황에 따라 다를 수 있다.
  - 최대한 JPA를 활용하는 것을 추천한다.
  - JPA로 사용하기 힘들 때는 엔티티 설계를 다시 한 번 생각해보는 것이 좋다.
  - Hibernate 인자로 `Sort` 클래스를 사용하여 자바 코드로 SQL을 명시할 수 있다.
    - `and()`로 한 테이블에 여러 정렬 전략을 사용할 수 있다.
  - `Repository`에 기본 CRUD가 아닌 로직이 있다면 반드시 테스트가 필요하다.
- [ ] `@Transactional(readOnly = true)`
  - 데이터베이스에서 쓰기와 읽기 트랜잭션이 나눠져 있고, 읽기 연산만 한다면 읽기 트랜잭션만 쓰는 것이 유리하다고 한다.
- [ ] Github와 같이 오픈되어 있는 곳에 민감한 정보를 올리는 경우
  - yml 파일 내에 민감한 정보를 담고 `.gitignore`로 Github에 올리지 않는다.
  - 빌드 설정으로 제외할 수 있다.
    - 하지만 사람이 직접해야 하므로 실수할 확률이 크다.
- Primitive Type과 Boxing Type
  - Primitive Type은 NULL Safe 하고, final 키워드를 사용하기 좋다. 왜냐하면 완전히 불변으로 만들어 주기 때문이다.
  - Boxing Type은 반드시 사용해야할 경우가 있다. 자바 컬렉션이나 엔티티에서 `Id` 필드를 만들 때 데이터베이스에 접근하기 전에는 id가 설정되지 않으므로 NULL일 때가 있어야 한다.
- 인수 테스트에서 `.jsonPath()`보다는 `.expectBody(DTO 클래스)`를 사용하여 가독성을 높일 수 있다.
  - `expectBody()`의 리턴값이 설정된 DTO 클래스를 반환하고 그 다음 줄에서 `getter()`를 통해 데이터를 검증할 수 있다.
  - 실수를 줄일 수 있다. `.jsonPath()`는 내부 스트링의 문법도 알아야 한다.
- `Service`의 의존 관계가 많아지면 줄일 수 있는 방법을 생각해야 한다.
  - 유틸성이나 로직이 비슷한 의존관계를 하나로 묶어서 분리하여 처리할 수도 있다.
- 테스트 환경에서 `application-properties`로 설정을 프로덕션과 다르게 해줄 때 단순히 `application-test.properties`와 테스트 클래스 위 어노테이션으로 `@ActiveProfiles("test")`만으로는 배포환경에서 사용할 수는 없다.
  - 인텔리제이 환경에서는 자동으로 잡아주는 듯 하다.
  - 배포 환경에서는 따로 설정이 더 필요하다.
- Restful URI
  - 명사형으로 적어주는 것이 좋았다.
  - `maximum-minimum`은 빼는 것이 좋겠다.
  - 공통된 URI는 `@RequestMapping("/공통URI")` 어노테이션을 이용하여 중복을 제거한다.