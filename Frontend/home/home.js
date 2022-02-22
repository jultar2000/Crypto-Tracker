import {
    createModal,
    createButtonFieldWithModal,
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
    let tableBody = document.getElementById('table-body');
    clearElementChildren(tableBody);
    Object.keys(coins).forEach(key => {
        tableBody.appendChild(createTableRow(key, coins));
    })
}

function createTableRow(key, coins) {
    let tr = document.createElement('tr');
    let modal = document.getElementById("modal");
    let closeButton = document.getElementById("close-button");
    let dt = document.getElementById('details-table')
    let object = coins[key];
    if(object['name'] == 'Bitcoin'){
        createModal(dt, object);
    }
  
    tr.appendChild(createTextField(object['market_cap_rank']));
    tr.appendChild(createTextField('image'));
    tr.appendChild(createTextField(object['name']));
    tr.appendChild(createTextField(object['current_price'] + ' USD'));
    tr.appendChild(createTextField(object['price_change_percentage_24h'].toFixed(2) + ' %'));
    tr.appendChild(createTextField(object['market_cap'] + ' USD'));
    tr.appendChild(createButtonFieldWithModal('Details', modal, closeButton));
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
        window.alert('The coin is now tracked!');
    } catch (err) {
        console.error(err);
    }
}