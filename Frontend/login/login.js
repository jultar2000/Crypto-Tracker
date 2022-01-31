import {getBackendURL} from '../utils/utils.js';

window.addEventListener('load', () => {
    setFunctionality();
});

function setFunctionality() {
    document.getElementById('login-button').onclick = loginUser;
}

function loginUser() {
    var username = document.getElementById('user-name').value;
    var password = document.getElementById('pass').value;
    var data = {
        "username": username,
        "password": password
      };

    var json = JSON.stringify(data);

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            var obj = JSON.parse(xhttp.response);
            localStorage.setItem("authenticationToken", obj.authenticationToken);
            localStorage.setItem("refreshToken", obj.refreshToken);
            localStorage.setItem("expiresAt", obj.expiresAt);
            localStorage.setItem("username", obj.username);
            window.location.href = "../home/home.html";
        }
    };

    xhttp.open("POST", getBackendURL() + '/auth/login', true);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(json);
}