import { getBackendURL, getFromSessionStorage } from '../utils/utils.js';

window.addEventListener('load', () => {
    fetchAndDisplayUser();
    setFunctionality();
});

async function fetchAndDisplayUser() {
    const response = await fetch(getBackendURL() + '/coins/user/profile', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + getFromSessionStorage('authenticationToken')
        },
        body: JSON.stringify()
    })
        .then(response => response.json())
        .then(data => {
            for (const [key, value] of Object.entries(data)) {
                let input = document.getElementById(key);
                if (input) {
                    input.value = value;
                }
            }
        });
}

function setFunctionality() {
    document.getElementById('submit-button').onclick = submit;
    document.getElementById('logout-button').onclick = logout;
}

async function submit() {
    const data = {
        'name': document.getElementById('name').value,
        'surname': document.getElementById('surname').value,
        'age': document.getElementById('age').value
    }
    const response = await fetch(getBackendURL() + '/coins/user/update', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + getFromSessionStorage('authenticationToken')
        },
        body: JSON.stringify(data)
    })
        .then(fetchAndDisplayUser());
}

async function logout() {
    const data = {
        'refreshToken': getFromSessionStorage('refreshToken'),
        'username': getFromSessionStorage('username')
    }
    const response = await fetch(getBackendURL() + '/auth/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + getFromSessionStorage('authenticationToken')
        },
        body: JSON.stringify(data)
    })
        .then(window.location.href = '../login/login.html');
}