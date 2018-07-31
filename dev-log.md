## 07/25
### TODO
1. 클라이언트가 인증 서버에서 받은 access token을 요청 바디에 넣어서 어플리케이션 서버로 보내면, 
엑세스 토큰을 받고 JWT를 발급 후 HTTP-only 쿠키에 넣어서 응답보내는 것 구현.

2. 통합 테스트 구현하기  

### 박진형
1. JWT를 발급해서 response에 실어주려고 할 때 PostLoginToken에서 꺼내옴 : DTO 역할
- credencial에 넣었을 때는 eraseCredential이 실행되어서 지워져있어서 null이 됨 : 무슨이유일까? 인증 후라면 credential을 삭제함, 인증 후 객체(생성자로 구분)를 생성한 것이라 credential을 삭제하는 것으로 생각됨

2. 통합테스트를 하지않으면 모든 경우의수에 대해서 하나씩 테스트해야함
3. 코드를 github에 공유할 때 테스트 키 값이라도 빼고 올리기, 칸만 남겨두고 지워버리기
4. TestRestTemplate를 Autowired 하지않으면 상대적인 URI를 사용할 수 없음
5. 쿠키에 setSecure를 설정하면 쿠키 목록에서는 없어짐(자바스크립트 API로 조회할 수 없음) HTTP 통신에서는 사용됨 XSS 공격으로부터는 자유로움
- 쿠키는 HTTP 통신할 때 자동으로 전달
- CSRF에 대한 공격 대비는 따로 서버에서 해야함
  
### 박재현
1. SuccessHandler에서 계속 PostLoginToken에 credential로 넣어둔 TokenDto를 꺼낼 때 null이난다.
	- PostLoginToken이 생성될 때는 분명 들어갈 때는 잘 들어간다.
	- 처음엔 다른 AuthenticationToken 객체가 들어오나 해서 로그를 찍어봤더니 아니다.
	- 버그 찾는 과정에서 또 다른 버그 발견: 인증 서버에서 받아온 json을 객체화해서 TokenDto로 만든건데, 알고보니 json이 제대로 맵핑이 안되어서 userId가 계속 널로 들어왔었다. - API 문서 제대로 읽자. 그리고 겟해서 꺼낼 때 널 체크 해주는 것도 좋겠다.
	    - 하지만 이것 때문에 PostLoginToken의 credential에 TokenDto가 꺼낼 때 널로 나오는 게 아니다. 
	    - PostLoginToken 생성할 때 모든 정보가 다 super(AuthenticationToken)에 잘 들어가는 것을 확인했음. 
	- 원인 발견: 어떤 이유인지는 모르겠지만 자구 eraseCredential이 호출되는 것 같다. 그래서 principal에 TokenDto를 넣으면 지워지지 않고 잘 들어간다. 하지만 토큰 오브젝트가 들어가면 credential이 지워지면서 널이 된다. 
	    - 찾아 보니 인증 후 객체는 credential이 필요 없게되고 se=nsitive data여서 스프링에서 내부적으로 eraseCredentials가 호출되게 구현된 것 같다!

2. Servlet의 Cookie 객체에 .secure(true)를 주면 클라이언트에서 자바스크립트 api로 쿠키 정보를 확인할 수가 없음. --> XSS 방지.

## 07/27
### TODO 
1. 쿠키 설정 ajax 처리에 대한 로직을 만듦 - redirect까지 해주거나 로그인버튼을 없애야함
2. 인증받은 클라이언트가 다시 OAuth 인증로직을 거치지않고 JWT 인증 로직을 거칠 수 있도록 JWT 인증 로직을 만든다.

—— JWT 인증 플로우 만들기 —— 
3. 발급해줄 때 JWT 만료기간 설정 
4. JWT 기간 만료되었을 때 인증받고 와 라고 /login 페이지로 리다이렉트 시키기

### 개발 과정에서 얻은 것
1. get하지말자 - 리팩토링 꼭 하기, 짤 땐 모르지만 하루만 지나도 쓰레기 코드가 눈에 보인다 특히 남에 상태를 끌어와서 제조하는 그런 나쁜 코드는 반드시 없애자
2. 지금 전략이 고정된 전략이 아닐 것이다 라는 생각을 가지고 코딩하기
- 테스트할 때도 편함

3. 액세스 키는 파일로 관리하기 - 하드코딩하지말기
- 공유하지만 않으면 됨
- properties에서 관리해서 injection 하려면 JWTGenerator가 스프링빈이어야함
- application.properties도 서버 설정 파일이기때문에 공유하지말자

4. REST API
- ajax는 302 처리를 하지못함 : 왜?

5. RequestMatcher 사용하는 것과 SecurityConfig의 configure 메소드에서 일일이 다 설정해주는 것이랑 똑같은 기능은 하지만 깔끔하게 관리하고, 필터마다 하나씩 설정해주려면 RequestMatcher를 사용하는 것으로 가야함

## 07/28
### TODO
1. 필터를 skip 하도록 설정해둔 엔드포인트들 (/login, /h2-console 등)이 필터로 들어옴.
    - Incognito mode로 하면 '로그인한 사용자가 아닙니다' 예외 발생 -> 확인해본 결과 incognito는 쿠키를 리퀘스에 담지 않아서 필터에서 예외 던짐.
    - 일반 모드는 예외는 안던져지지만 필터로 들어오긴 함.

해결 방안:
스프링 시큐리티 전체가 url을 무시하게 하느냐, 아니면 특정 필터가 url을 무시하게 하느냐의 차이가 있을 수 있다.
- resources에 있는 자원 같은 경우나 h2-console은 시큐리티 자체에서 무시를 하게끔 하는 게 좋을 것 같다.
- 특정 url에 대한 요청은 필터마다 다르게 막아야하기 때문에 필터에 걸리도록 설정.

## 07/30
### TODO 
1. jwt 프로바이더 만들기
2. jwt 만료기간 체크 
- 서버 체크 : secure cookie를 사용하고 있기때문에 만료기간이 다되었을 때 /login 으로 가게끔 리다이렉트

3. 성공 핸들러 -> chain 그대로 진행되도록
4. 실패 핸들러 만들기 

### 개발과정에서 얻은 것
1. JWT 프로바이더 생성
- decode 되었을 때(인증성공) Account를 만듦 : request에 실어줄 정보를 가짐

2. JWT Manager - encode, decode 역할
3. JPA를 사용할 때 필드 타입으로 상속 관계의 최상위 타입을 설정하면 어떻게 저장되는가?
- FormAccount와 SocialAccount는 엄연히 다른 정보를 가져야하는데 이 차이점을 극복하지않고 그대로 인증을 진행해도 올바른 객체 구성인가?

4. 쿠키 httponly 만들기
- 콘솔 상에서는 존재하나 자바스크립트 API로 조회불가 하도록 만듦


## 07/31
### TODO 
1. 실패 핸들러 손보기
2. 리팩토링하기
- Template를 통해서 인증서버에 verify를 요청함 : 해당 리소스를 여러 사용자의 요청에 의해 사용될 때 어떻게 처리할 것인가
- 쿠키 삭제 : JWT 쿠키 관리자를 따로 만들어서 생성, 삭제를 담당하도록 하기(관리자를 따로 빼두면 쿠키가 어떤 상태를 가지고 만들어지는지 몰라도 되고, 쿠키에 대해 변경이 생기면 한 곳에서 변경을 할 수 있으니 관리에도 좋다고 생각됨)

3. 개발 계획, 객체 설계하기 - 방 리뷰 포스트 기능

### 개발과정에서 얻은 것
1. Optional, Stream API를 활용한 코드 간결화
2. favicon.ico로 redirect 되는 현상 : security가 적용되기때문에 ignore 설정해주면 됨
- 자원 요청에 대해서 모두 ignore 하도록
- security 관련 필터에서 ignore 할 것과 필터 객체마다 ignore 함 : 메소드 오버라이드와 필터 객체 생성자에서 requestMatcher 설정해주는 것

3. ObejctMapper(Jackson)을 사용할 때 올바르지 않은 인증 정보가 넘어왔을 때 로그인을 제대로 하도록 로그인 페이지로 이동시키기
- API 정의를 가보면 Exception을 발생시킴 : failureHandler에서 일괄적으로 처리하기위해 AuthenticationException을 상속받는 커스텀 예외를 만들고 발생(throw)시킴

4. 인증서버 프로바이더 마다 인증 확인 요청 Template 분리
- 객체 사용 분산
- 서비스 프로바이더의 인증 확인 방식에 변경이 생겼을 때 개별적으로 대처 : 페이스북은 카카오, 네이버와는 다른 방식을 취하고 있음(https://developers.facebook.com/docs/graph-api/reference/v2.2/user)

5. enum 분리기준
- 다른 곳에서 사용한다면 별도로 분리해두고 관리하기
- 외부에서 파라미터로 넘겨줄 때 값을 제한하고싶다면 enum으로 분리하기

6. 인증서버에 액세스토큰 verify 하기
- 기존에는 싱글턴으로 인증서버에 요청하는 객체를 관리 및 사용하고 있었음 : 동시 처리를 해야할 때(요청이 겹쳐질 때) 인스턴스를 어떻게 사용할지
- 새롭게 인스턴스를 만들어주거나 인스턴스 풀에 적정 개수의 인스턴스를 관리하면서 요청 때 꺼내주고 돌려받는 형식으로 개발하거나(쓰레드풀)
- 그런줄 알았는데 템플릿이 상태값을 가지지 않기 때문에 동시성 제어를 할 필요가 없음 : 해당 객체의 메소드를 각각이 호출 했을 때 각각 스택 프레임이 생김 -> 스택은 쓰레드 간 공유하지않는 메모리 영역이므로 같이 사용해도 무관함(스프링빈과 동일)
