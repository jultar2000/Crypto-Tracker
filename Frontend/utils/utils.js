const axios = require('axios').default;

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

const instance = axios.create({
    baseURL: getBackendURL(),
    headers: {
        "Content-Type": "application/json",
    },
});

// instance.interceptors.request.use(
//     (config) => {
//         const token = getFromSessionStorage('authenticationToken');
//         if (token) {
//             config.headers['Authorization'] = 'Bearer ' + token;
//         }
//         return config;
//     },
//     (error) => {
//         return Promise.reject(error);
//     }
// );

// instance.interceptors.response.use(
//     (res) => {
//         return res;
//     },
//     async (err) => {
//         const originalConfig = err.config;
//         if (err.response) {
//             // Access Token was expired
//             if (err.response.status === 401 && !originalConfig._retry) {
//                 originalConfig._retry = true;
//                 try {
//                     const rs = await refreshToken();
//                     const { accessToken } = rs.data;
//                     window.localStorage.setItem("accessToken", accessToken);
//                     instance.defaults.headers.common["x-access-token"] = accessToken;
//                     return instance(originalConfig);
//                 } catch (_error) {
//                     if (_error.response && _error.response.data) {
//                         return Promise.reject(_error.response.data);
//                     }
//                     return Promise.reject(_error);
//                 }
//             }
//             if (err.response.status === 403 && err.response.data) {
//                 return Promise.reject(err.response.data);
//             }
//         }
//         return Promise.reject(err);
//     }
// );