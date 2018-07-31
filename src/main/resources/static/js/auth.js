var providers = Object.freeze({"kakao": "KAKAO", "fb": "FACEBOOK"});

Kakao.init('2d93affaab26c2f0d410f1426165fed0');
Kakao.Auth.createLoginButton({
    container: '#kakao-login-btn',
    success: function(authObj, providerName) {
        saveAuthInfo(JSON.stringify(authObj), providers.kakao);
    },
    fail: function(err) {
        alert("로그인 실패");
    }
});

function saveAuthInfo(authObj, providerName) {
    var accessToken = JSON.parse(authObj);
    var token = {
        provider : providerName,
        token : accessToken.access_token
    };

    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if (req.readyState === 4 && req.status === 200) {
            location.replace(req.getResponseHeader("Location"));
        }
    };
    req.open('POST', '/oauth', true);
    req.setRequestHeader('Content-Type', 'application/json');
    req.send(JSON.stringify(token));
}

