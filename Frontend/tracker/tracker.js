import {
    getBackendURL,
    createTextField,
    getFromLocalStorage,
    createButtonField,
    clearElementChildren
} from '../utils/utils.js';


window.addEventListener('load', () => {
    fetchAndDisplayUserCoins();
});

function fetchAndDisplayUserCoins() {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayCoins(JSON.parse(this.responseText));
        }
    }
    xhttp.open("GET", getBackendURL() + '/coins/user', true);
    xhttp.setRequestHeader('Authorization', 'Bearer ' + getFromLocalStorage("authenticationToken"));
    xhttp.send();
}

function displayCoins(coins) {
    var tableBody = document.getElementById('table-body');
    clearElementChildren(tableBody);
    let i = 1;
    Object.keys(coins).forEach(id => {
        tableBody.appendChild(createTableRow(id, coins, i));
        i++;
    })
}

function createTableRow(id, coins, i) {
    var tr = document.createElement('tr');
    var coinName = id[0].toUpperCase() + id.substring(1);
    tr.appendChild(createTextField(i));
    tr.appendChild(createTextField("image"));
    tr.appendChild(createTextField(coinName));
    tr.appendChild(createTextField(coins[id]["usd"] + ' USD'));
    tr.appendChild(createTextField(coins[id]["usd_24h_change"].toFixed(2) + ' %'));
    tr.appendChild(createTextField(coins[id]["usd_market_cap"] + ' USD'));
    tr.appendChild(createButtonField("Details", () => window.location.href = "../details/details.html"));
    tr.appendChild(createButtonField("Untrack", () => untrackCoin(id)));
    return tr;
}

function untrackCoin(id) {
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            fetchAndDisplayUserCoins();
        }
    }
    const data = {
        "coinName": id
    }
    xhttp.open("DELETE", getBackendURL() + "/coins/user", true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.setRequestHeader('Authorization', 'Bearer ' + getFromLocalStorage("authenticationToken"));
    xhttp.send(JSON.stringify(data));
}