export function getBackendURL(){
    return "http://localhost:8084/api";
}

export function getParameterByName(name) {
    return new URLSearchParams(window.location.search).get(name);
}
