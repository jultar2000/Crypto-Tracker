import { getBackendURL } from '../utils/utils.js';


window.addEventListener('load', () => {
    getAllCoins();
});

function getAllCoins() {

    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.response === 200) {

        }
    }

    xhttp.open("GET", getBackendURL() + '/coins');
    xhttp.setRequestHeader('Content-Type', 'application/json');
    xhttp.send();
}







