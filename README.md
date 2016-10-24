version.txt
--
gradlew build
--


0.3.5.1
(2016.10.24)
Result 객체의 버그 수정
수정 전 : return (int) (Long.parseLong(ts) - Long.parseLong(o.ts));

Long 타입 뺄셈의 결과가 큰 롱타입의 숫자가 나올경우 return을 마이너스로 하게 됨.
--> Arrays.sort시 ComparableTimSort가 수행되었고, 비교 연산(compareTo)의 버그가 있기 때문에 문제 발생.
java.lang.IllegalArgumentException: Comparison method violates its general contract



0.3.5
로깅 INFO level
/apps/restapi-test/restapi-test-server.log
테스트 data의 갯수 444개로 고정



0.3.4.2
from, to를 long 타입으로 바꾸면서 안 바뀐 것들이 있어서..
Result 객체에서 compareTo와 같은 것들을
마저 수정함.

0.3.4.1
yyyymmddhhmiss -> long 타입으로

--------------------------------------------------------------------------------------
0.3.4
OAuth client_credentials 추가

POST : http://localhost:8888/restapi/token
 입력 :
  grant_type=client_credentials
  client_id=4a60f7b68f9e47d89eb73f0dc93e2ea6
  client_secret=0543c3ca1e7043a18719eb9f73c0f63c
 출력 :
   token_type
   access_token

GET: http://localhost:8888/restapi/data
    ?from=20160527000000&to=20160527151959&limit=10
 입력 :
  Header에 Authorization에 token_type+' '+access_token

--------------------------------------------------------------------------------------
0.3.3
gobblin-azkaban 관련내용 삭제
java 1.7

** from, to를 timestamp로 넣어야 함을 잊지 말길...!!!

--------------------------------------------------------------------------------------
--> 삭제
0.3.2
gobblin-azkaban test를 위한 ocp-gobblin.jar 추가
AnnotationUtils 활용

--------------------------------------------------------------------------------------

0.3.1
1. from, to에 타임스탬프 형식으로 입력(yyyymmddhhmmss)
2. ts 대신 ts로 from에서 to 사이의 값을 랜덤, 순서대로 출력하되 array 길이는 10-500
3. login하지 않아도 되는 URL 추가

--------------------------------------------------------------------------------------

0.3.0
/greeting/test 추가.(get/post/put/delete) (로그인이나, header 정보 필요 없음)

ex>
__GET__
http://sinbaram02:8888/greeting/test?from=28&to=40
{
    result = object array, { ts:28 초과, 40 이하, v : random }
    currentTimeMills = System.currentTimeMills
    from = print input parameter(28)
    to = print input parameter(40)
    done = true
}


__POST__
http://sinbaram02:8888/greeting/test?from=28&to=40
{
  "msg": "post complete.",
  "currentTimeMillis": 1448341758286,
  "from": 28,
  "to": 40
}


__PUT__
http://sinbaram02:8888/greeting/test?from=28&to=40
{
  "msg": "put complete.",
  "currentTimeMillis": 1448341772395,
  "from": 28,
  "to": 40
}


__DELETE__
http://sinbaram02:8888/greeting/test?from=28&to=40
{
  "msg": "delete complete.",
  "currentTimeMillis": 1448341781567,
  "from": 28,
  "to": 40
}

--------------------------------------------------------------------------------------

0.2.3
+ limit 추가 시 최대 출력 제한을 둘 수 있음
    limit을 초과하는 데이터 존재시,
    done = false,
    nextUrl을 통해 확인 가능.

http://sinbaram02:8888/sample?from=1&to=8&limit=3
{
  "result": [
    {
      "ts": 2,
      "v": 470
    },
    {
      "ts": 3,
      "v": 973
    },
    {
      "ts": 4,
      "v": 139
    }
  ],
  "from": "1",
  "to": "8",
  "nextUrl": "http://sinbaram02:8888/sample?from=1&to=8&limit=3&starting=5",
  "done": "false"
}

--------------------------------------------------------------------------------------

0.2.x
/sample 로그인, http header(Authorization)으로 권한
로그인 : http://sinbaram02:8888/sample/login?username=ocp&password=1234
인증 : return되는 access_token 값을 이후 http header Authorization에 추가해서 보내줘야 함!
 : 세션이 만료되면 재로그인을 해서 access_token을 받아야함.
사용 : 로그인 후(ocp/1234) header에 Authrization에 access_token 넣은 후
내용


--------------------------------------------------------------------------------------

0.1.x
/sample 기본 from, to RestAPI
http://sinbaram02:8888/sample?from=1&to=8

--------------------------------------------------------------------------------------