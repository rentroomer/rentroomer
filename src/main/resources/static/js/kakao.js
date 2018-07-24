Kakao.init('2d93affaab26c2f0d410f1426165fed0');
Kakao.Auth.createLoginButton({
    container: '#kakao-login-btn',
    success: function(authObj) {
        saveAuthInfo(JSON.stringify(authObj));
    },
    fail: function(err) {
        alert("로그인 실패");
    }
});

function saveAuthInfo(authObj) {
    var accessToken = JSON.parse(authObj);
    var token = {
        provider : "KAKAO",
        token : accessToken.access_token
    };

    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if (req.readyState === 4 && req.status === 200) {
            // redirect uri 받아서 겟요청.
        } else {
            console.log('에러');
            // 실패 처리
        }
    };
    req.open('POST', '/oauth', true);
    req.setRequestHeader('Content-Type', 'application/json');
    req.send(JSON.stringify(token));
}

