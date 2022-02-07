import { getBackendURL } from '../utils/utils.js';

window.addEventListener('load', () => {
    setFunctionality();
});

function setFunctionality() {
    document.getElementById('login-button').onclick = login;
}

function login() {
    const data = {
        'username': document.getElementById('user-name').value,
        'password': document.getElementById('pass').value
    };
    axios.post(getBackendURL() + '/auth/login', data, {
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            response = response.data;
            sessionStorage.setItem('authenticationToken', response.authenticationToken);
            sessionStorage.setItem('refreshToken', response.refreshToken);
            sessionStorage.setItem('expiresAt', response.expiresAt);
            sessionStorage.setItem('username', response.username);
            window.location.href = '../home/home.html';
        })
        .catch(err => console.error(err));
}