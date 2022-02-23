export function getBackendURL() {
    return 'http://localhost:8084/api';
}

export function clearElementChildren(element) {
    while (element.firstChild) {
        element.removeChild(element.firstChild);
    }
}

export function getParameterByName(name) {
    return new URLSearchParams(window.location.search).get(name);
}

export function getFromSessionStorage(type) {
    return sessionStorage.getItem(type)
}

export function createTextField(text) {
    const td = document.createElement('td');
    td.appendChild(document.createTextNode(text));
    return td;
}

export function setTextNode(id, text) {
    let element = document.getElementById(id);
    element.appendChild(document.createTextNode(text));
}

export function createButtonField(text, action) {
    const td = document.createElement('td');
    const button = document.createElement('button');
    button.appendChild(document.createTextNode(text));
    td.appendChild(button);
    button.addEventListener('click', action);
    return td;
}

export function createButtonFieldWithAtribute(text, atributeName, action) {
    const td = document.createElement('td');
    const button = document.createElement('button');
    button.appendChild(document.createTextNode(text));
    button.setAttribute("class", atributeName);
    td.appendChild(button);
    button.addEventListener('click', action);
    return td;
}

export function createButtonFieldWithDetailsModal(text, object, mt) {
    let modal = createDetailsModal(object, mt);
    const td = createButtonFieldWithAtribute(text, "details-button",  () => {
        modal.classList.add('active');
    });
    td.setAttribute("class", "details-button");
    return td;
}

function createDetailsModal(object, mt) {
    let modal_container = document.createElement("div");
    let modal_header = document.createElement("div");
    let modal_title = document.createElement("div");
    let close_button = document.createElement("button");
    let dt = createDetailsTable();

    modal_container.setAttribute("class", "modal");
    modal_header.setAttribute("class", "modal-header");
    modal_title.setAttribute("class", "title");
    close_button.setAttribute("class", "close-button");

    modal_title.appendChild(document.createTextNode("Coin Details"));
    modal_header.appendChild(modal_title);
    close_button.innerHTML = "&times;";
    modal_header.appendChild(close_button);
    modal_container.appendChild(modal_header);
    modal_container.appendChild(dt);

    close_button.onclick = function () {
        modal_container.classList.remove('active');
    }

    fillUpDetailsModal(dt, object);
    mt.parentNode.insertBefore(modal_container, mt);
    return modal_container;
}

function createDetailsTable() {
    let table = document.createElement("table");
    table.setAttribute("id", "details-table");
    addNewDataToTable(table, "Market cap rank:");
    addNewDataToTable(table, "Name:");
    addNewDataToTable(table, "Price:");
    addNewDataToTable(table, "Percent change 24h:");
    addNewDataToTable(table, "Market cap:");
    addNewDataToTable(table, "Total volume:");
    addNewDataToTable(table, "Circulating supply:");
    addNewDataToTable(table, "Total supply:");
    addNewDataToTable(table, "ATH:");
    return table;
}

function addNewDataToTable(table, text) {
    let tr = document.createElement('tr');
    let th = document.createElement('th');
    th.appendChild(document.createTextNode(text));
    tr.appendChild(th);
    table.appendChild(tr);
}

function fillUpDetailsModal(dt, object) {
    dt.rows[0].appendChild(createTextField(object['market_cap_rank']));
    dt.rows[1].appendChild(createTextField(object['name']));
    dt.rows[2].appendChild(createTextField(object['current_price'] + ' USD'));
    dt.rows[3].appendChild(createTextField(object['price_change_percentage_24h'].toFixed(2) + ' %'));
    dt.rows[4].appendChild(createTextField(object['market_cap'] + ' USD'));
    dt.rows[5].appendChild(createTextField(object['total_volume']));
    dt.rows[6].appendChild(createTextField(object['circulating_supply']))
    dt.rows[7].appendChild(createTextField(object['total_supply']));
    dt.rows[8].appendChild(createTextField(object['ath'] + ' USD'));
}

axios.interceptors.request.use(
    (config) => {
        const token = getFromSessionStorage('authenticationToken');
        if (token) {
            config.headers['Authorization'] = 'Bearer ' + token;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

axios.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        const originalRequest = error.config;
        if (error.response.status === 403 && !originalRequest._retry) {
            originalRequest._retry = true;
            refreshToken();
            return axios(originalRequest);
        }
        return Promise.reject(error);
    }
);

async function refreshToken() {
    const data = {
        'refreshToken': getFromSessionStorage('refreshToken'),
        'username': getFromSessionStorage('username')
    };
    try {
        const response = await axios.post(getBackendURL() + '/auth/refresh/token', data, {
            headers: {
                'Content-Type': 'application/json',
            }
        });
        sessionStorage.setItem('authenticationToken', response.data.authenticationToken);
        sessionStorage.setItem('refreshToken', response.data.refreshToken);
        sessionStorage.setItem('expiresAt', response.data.expiresAt);
        sessionStorage.setItem('username', response.data.username);
    } catch (err) {
        console.error(err);
    }
}