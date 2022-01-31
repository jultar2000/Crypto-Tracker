export function getBackendURL(){
    return "http://localhost:8084/api";
}

export function clearElementChildren(element) {
    while (element.firstChild) {
        element.removeChild(element.firstChild);
    }
}

export function getParameterByName(name) {
    return new URLSearchParams(window.location.search).get(name);
}

export function getFromLocalStorage(type){
   return localStorage.getItem(type)
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

export function createButtonField(text, action){
    const td = document.createElement('td');
    const button = document.createElement('button');
    button.appendChild(document.createTextNode(text));
    td.appendChild(button);
    button.addEventListener('click', action);
    return td;
}