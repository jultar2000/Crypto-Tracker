import { getBackendURL } from '../utils/utils.js';

window.addEventListener('load', () => {
    setFunctionality();
});

function setFunctionality() {
    document.getElementById('login-button').onclick = login;
}

async function login() {
    const data = {
        'username': document.getElementById('user-name').value,
        'password': document.getElementById('pass').value
    };
    const response = await fetch(getBackendURL() + '/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            sessionStorage.setItem('authenticationToken', data.authenticationToken);
            sessionStorage.setItem('refreshToken', data.refreshToken);
            sessionStorage.setItem('expiresAt', data.expiresAt);
            sessionStorage.setItem('username', data.username);
            window.location.href = '../home/home.html';
        });
}