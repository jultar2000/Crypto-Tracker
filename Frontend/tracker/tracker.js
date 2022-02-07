import {
    getBackendURL,
    createTextField,
    getFromSessionStorage,
    createButtonField,
    clearElementChildren
} from '../utils/utils.js';


window.addEventListener('load', () => {
    fetchAndDisplayUserCoins();
});

async function fetchAndDisplayUserCoins() {
    const response = await fetch(getBackendURL() + '/coins/user', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + getFromSessionStorage('authenticationToken'),
        }
    })
        .then(response => response.json())
        .then(data => {
            displayCoins(data);
        })
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
    tr.appendChild(createTextField('image'));
    tr.appendChild(createTextField(coinName));
    tr.appendChild(createTextField(coins[id]['usd'] + ' USD'));
    tr.appendChild(createTextField(coins[id]['usd_24h_change'].toFixed(2) + ' %'));
    tr.appendChild(createTextField(coins[id]['usd_market_cap'] + ' USD'));
    tr.appendChild(createButtonField('Details', () => window.location.href = '../details/details.html'));
    tr.appendChild(createButtonField('Untrack', () => untrack(id)));
    return tr;
}

async function untrack(id) {
    const data = {
        'coinName': id
    };
    const response = await fetch(getBackendURL() + '/coins/user', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + getFromSessionStorage('authenticationToken'),
        },
        body: JSON.stringify(data)
    })
        .then(fetchAndDisplayUserCoins());
}