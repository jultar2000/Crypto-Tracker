import { getBackendURL } from "../utils/utils.js";

window.addEventListener("load", () => {
    setFunctionality();
});

function setFunctionality() {
    document.getElementById("login-button").onclick = login;
}

async function login() {
    const data = {
        "username": document.getElementById("user-name").value,
        "password": document.getElementById("pass").value
    };
    try {
        const response = await axios.post(getBackendURL() + "/auth/login", data, {
            headers: {
                "Content-Type": "application/json"
            }
        });
        sessionStorage.setItem("authenticationToken", response.data.authenticationToken);
        sessionStorage.setItem("refreshToken", response.data.refreshToken);
        sessionStorage.setItem("expiresAt", response.data.expiresAt);
        sessionStorage.setItem("username", response.data.username);
        window.location.href = "../home/home.html";
    } catch (err) {
        err => console.error(err);
    }
}