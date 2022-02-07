import {
    getBackendURL,
    createTextField,
    getFromSessionStorage,
    createButtonField,
    clearElementChildren
} from '../utils/utils.js';

window.addEventListener('load', () => {
    fetchAndDisplayAllCoins();

});

async function fetchAndDisplayAllCoins() {
    const response = await fetch(getBackendURL() + '/coins/all', {
        method: 'GET',
        // headers: {
        //     'Authorization': 'Bearer ' + getFromSessionStorage('authenticationToken')
        // }
    })
        .then(response => response.json())
        .then(data => {
            displayCoins(data);
        });
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
    tr.appendChild(createTextField(object['market_cap_rank']));
    tr.appendChild(createTextField('image'));
    tr.appendChild(createTextField(object['name']));
    tr.appendChild(createTextField(object['current_price'] + ' USD'));
    tr.appendChild(createTextField(object['price_change_percentage_24h'].toFixed(2) + ' %'));
    tr.appendChild(createTextField(object['market_cap'] + ' USD'));
    tr.appendChild(createButtonField('Details', () => window.location.href = '../details/details.html'));
    tr.appendChild(createButtonField('Track', () => track(object['id'])));
    return tr;
}

async function track(id) {
    const data = {
        'coinName': id
    };
    const response = await fetch(getBackendURL() + '/coins/user', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + getFromSessionStorage('authenticationToken')
        },
        body: JSON.stringify(data)
    });
}