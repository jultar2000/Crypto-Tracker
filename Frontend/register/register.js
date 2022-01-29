import {getBackendURL} from '../utils/utils.js'
window.addEventListener('load', () => {
    setFunctionality();
});

function setFunctionality() {
    document.getElementById('register-button').onclick = registerUser;
}

function registerUser() {
    var username = document.getElementById('user-name').value;
    var password = document.getElementById('pass').value;
    var email = document.getElementById('email').value;

    var data = {
        "username": username,
        "password": password,
        "email": email,
      };

    var json = JSON.stringify(data);

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log("OK");
        } 
    };

    xhttp.open("POST", getBackendURL() + '/auth/signup', true);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(json);
}



