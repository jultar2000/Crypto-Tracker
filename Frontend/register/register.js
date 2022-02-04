import {getBackendURL} from '../utils/utils.js';
window.addEventListener('load', () => {
    setFunctionality();
});

function setFunctionality() {
    document.getElementById('register-button').onclick = registerUser;
}

function registerUser() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            console.log("OK");
        } 
    };
    const data = {
        "username": document.getElementById('user-name').value,
        "password": document.getElementById('pass').value,
        "email": document.getElementById('email').value,
      };
    xhttp.open("POST", getBackendURL() + '/auth/signup', true);
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send(JSON.stringify(data));
}



