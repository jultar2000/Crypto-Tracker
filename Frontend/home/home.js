import {
    getBackendURL,
    createTextField,
    createButtonField,
    clearElementChildren
} from '../utils/utils.js';

window.addEventListener('load', () => {
    fetchAndDisplayAllCoins();
});

async function fetchAndDisplayAllCoins() {
    try {
        const response = await axios.get(getBackendURL() + '/coins/all');
        displayCoins(response.data);
    } catch (err) {
        console.error(err);
    }
}

function displayCoins(coins) {
    var tableBody = document.getElementById('table-body');
    clearElementChildren(tableBody);
    Object.keys(coins).forEach(key => {
        tableBody.appendChild(createTableRow(key, coins));
    })
}

function createTableRow(key, coins) {
    var modal = document.getElementById("modal");
    var tr = document.createElement('tr');
    var object = coins[key];
    tr.appendChild(createTextField(object['market_cap_rank']));
    tr.appendChild(createTextField('image'));
    tr.appendChild(createTextField(object['name']));
    tr.appendChild(createTextField(object['current_price'] + ' USD'));
    tr.appendChild(createTextField(object['price_change_percentage_24h'].toFixed(2) + ' %'));
    tr.appendChild(createTextField(object['market_cap'] + ' USD'));
    tr.appendChild(createButtonField('Details', () => modal.style.display ="block"));
    tr.appendChild(createButtonField('Track', () => track(object['id'])));
    return tr;
}

async function track(id) {
    const data = {
        'coinName': id
    };
    try {
        await axios.put(getBackendURL() + '/coins/user', data, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
        window.alert('Coin is now being tracked!');
    } catch (err) {
        console.error(err);
    }
}