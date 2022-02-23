import { getBackendURL } from "../utils/utils.js";
window.addEventListener("load", () => {
    setFunctionality();
});

function setFunctionality() {
    document.getElementById("register-button").onclick = register;
}

async function register() {
    const data = {
        "username": document.getElementById("user-name").value,
        "password": document.getElementById("pass").value,
        "email": document.getElementById("email").value
    };
    try {
        await axios.post(getBackendURL() + "/auth/signup", data, {
            headers: {
                "Content-Type": "application/json"
            }
        });
        window.alert("Please verify your email in order to complete registration!");
        window.location.reload(true);
    } catch (err) {
        err => console.error(err);
    }
}