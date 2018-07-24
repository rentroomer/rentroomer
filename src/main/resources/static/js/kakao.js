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
    localStorage.setItem("auth", authObj);
}

