import {getBackendURL, getFromLocalStorage} from '../utils/utils.js';

window.addEventListener('load', () => {
    setFunctionality();
    fetchAndDisplayUser();
});

function fetchAndDisplayUser() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            //displayUser(JSON.parse(this.responseText));
        }
    }
    xhttp.open("GET", getBackendURL() + '/coins/user/profile', true);
    xhttp.setRequestHeader('Authorization', 'Bearer ' + getFromLocalStorage("authenticationToken"));
    xhttp.send();
}

function displayUser(user) {
    // document.getElementById('name');
    // document.getElementById('surname');
    // document.getElementById('age');
}

function setFunctionality() {
    document.getElementById('submit-button').onclick = submit;
}

function submit() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState === 4 && this.status === 202){

        }
    }
    const data =  {
        "name": document.getElementById('name').value,
        "surname": document.getElementById('surname').value,
        "age": document.getElementById('age').value
    }
    xhttp.open("PUT", getBackendURL() + "/coins/user/update", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader('Authorization', 'Bearer ' + getFromLocalStorage("authenticationToken"));
    xhttp.send(JSON.stringify(data));
}
