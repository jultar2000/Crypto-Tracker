import { getBackendURL } from '../utils/utils.js';
window.addEventListener('load', () => {
    setFunctionality();
});

function setFunctionality() {
    document.getElementById('register-button').onclick = register;
}

async function register() {
    const data = {
        'username': document.getElementById('user-name').value,
        'password': document.getElementById('pass').value,
        'email': document.getElementById('email').value
    };
    const response = await fetch(getBackendURL() + '/auth/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    });
}