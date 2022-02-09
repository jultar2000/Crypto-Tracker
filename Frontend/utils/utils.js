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
    } catch(err) {
        console.error(err);
    }
}