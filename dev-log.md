## 07/25
### TODO
1. 클라이언트가 인증 서버에서 받은 access token을 요청 바디에 넣어서 어플리케이션 서버로 보내면, 
엑세스 토큰을 받고 JWT를 발급 후 HTTP-only 쿠키에 넣어서 응답보내는 것 구현.

2. 통합 테스트 구현하기  

### 박진형

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

