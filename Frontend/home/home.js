import {
    getBackendURL,
    createTextField,
    createButtonField,
    clearElementChildren
} from '../utils/utils.js';

window.addEventListener('load', () => {
    fetchAndDisplayAllCoins();

});

function fetchAndDisplayAllCoins() {
    axios.get(getBackendURL() + '/coins/all')
        .then(response => {
            displayCoins(response.data);
        })
        .catch(err => console.error(err));
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

function track(id) {
    const data = {
        'coinName': id
    };
    axios.put(getBackendURL() + '/coins/user', data, {
        headers: {
            'Content-Type': 'application/json',
        }
    })
        .catch(err => console.error(err));
}