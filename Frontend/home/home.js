import {
    getBackendURL,
    createTextField,
    getFromLocalStorage,
    createButtonField,
    clearElementChildren
} from '../utils/utils.js';


window.addEventListener('load', () => {
    getAllCoins();
    
});

function getAllCoins() {
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayUsers(JSON.parse(this.responseText));
        }
    }
    xhttp.open("GET", getBackendURL() + '/coins/all');
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader('Authorization', 'Bearer ' + getFromLocalStorage("authenticationToken"));
    xhttp.send();
}

function displayUsers(coins) {
    var tableBody = document.getElementById('table-body');
    clearElementChildren(tableBody);
    for (let i = 0; i < Object.keys(coins).length; i++) {
        tableBody.appendChild(createTableRow(i, coins));
    }
}

function createTableRow(i, coins) {
    var tr = document.createElement('tr');
    var object = coins[i];
    tr.appendChild(createTextField(object["market_cap_rank"]));
    tr.appendChild(createTextField("image"));
    tr.appendChild(createTextField(object["name"]));
    tr.appendChild(createTextField(object["current_price"] + ' USD'));
    tr.appendChild(createTextField(object["price_change_percentage_24h"] + ' %'));
    tr.appendChild(createTextField(object["market_cap"] + ' USD'));
    tr.appendChild(createButtonField("Details"), null);
    tr.appendChild(createButtonField("Track"), null);
    return tr;
}








