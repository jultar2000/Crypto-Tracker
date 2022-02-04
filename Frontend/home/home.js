import {
    getBackendURL,
    createTextField,
    getFromLocalStorage,
    createButtonField,
    clearElementChildren
} from '../utils/utils.js';

window.addEventListener('load', () => {
    fetchAndDisplayAllCoins();
  
});

function fetchAndDisplayAllCoins() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayCoins(JSON.parse(this.responseText));
        }
    }
    xhttp.open("GET", getBackendURL() + '/coins/all');
    xhttp.setRequestHeader('Authorization', 'Bearer ' + getFromLocalStorage("authenticationToken"));
    xhttp.send();
}

function displayCoins(coins) {
    var tableBody = document.getElementById('table-body');
    clearElementChildren(tableBody);
    Object.keys(coins).forEach(key => {
        tableBody.appendChild(createTableRow(key, coins));
    })
}

function createTableRow(key, coins) {
    var tr = document.createElement('tr');
    var object = coins[key];
    tr.appendChild(createTextField(object["market_cap_rank"]));
    tr.appendChild(createTextField("image"));
    tr.appendChild(createTextField(object["name"]));
    tr.appendChild(createTextField(object["current_price"] + ' USD'));
    tr.appendChild(createTextField(object["price_change_percentage_24h"].toFixed(2) + ' %'));
    tr.appendChild(createTextField(object["market_cap"] + ' USD'));
    tr.appendChild(createButtonField("Details", () => window.location.href = "../details/details.html"));
    tr.appendChild(createButtonField("Track", () => trackCoin(object["id"])));
    return tr;
}

function trackCoin(id) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
          console.log("sent");
        }
    }
    const data = {
        "coinName": id
    }
    xhttp.open("PUT", getBackendURL() + "/coins/user", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader('Authorization', 'Bearer ' + getFromLocalStorage("authenticationToken"));
    xhttp.send(JSON.stringify(data));
}