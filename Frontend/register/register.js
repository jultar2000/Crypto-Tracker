import { getBackendURL } from '../utils/utils.js';
window.addEventListener('load', () => {
    setFunctionality();
});

function setFunctionality() {
    document.getElementById('register-button').onclick = register;
}

function register() {
    const data = {
        'username': document.getElementById('user-name').value,
        'password': document.getElementById('pass').value,
        'email': document.getElementById('email').value
    };
    axios.post(getBackendURL() + '/auth/signup', data, {
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .catch(err => console.error(err));
}