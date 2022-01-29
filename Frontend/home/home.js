import {getBackendURL} from '../utils/utils.js'

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
            console.log("OK");
        }
    };

    xhttp.open("POST", getBackendURL() + '/auth/login', true);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(json);
}




