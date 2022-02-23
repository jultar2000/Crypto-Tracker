import { getBackendURL, getFromSessionStorage } from "../utils/utils.js";

window.addEventListener("load", () => {
    fetchAndDisplayUser();
    setFunctionality();
});

async function fetchAndDisplayUser() {
    try {
        const response = await axios.get(getBackendURL() + "/coins/user/profile");
        for (const [key, value] of Object.entries(response.data)) {
            let input = document.getElementById(key);
            if (input) {
                input.value = value;
            }
        }
    } catch (err) {
        console.error(err);
    }
}

function setFunctionality() {
    document.getElementById("submit-button").onclick = submit;
    document.getElementById("logout-button").onclick = logout;
}

async function submit() {
    const data = {
        "name": document.getElementById("name").value,
        "surname": document.getElementById("surname").value,
        "age": document.getElementById("age").value
    }
    try {
        await axios.put(getBackendURL() + "/coins/user/update", data, {
            headers: {
                "Content-Type": "application/json",
            }
        });
        fetchAndDisplayUser();
    } catch (err) {
        console.error(err);
    }
}

async function logout() {
    const data = {
        "refreshToken": getFromSessionStorage("refreshToken"),
        "username": getFromSessionStorage("username")
    }
    try {
        const response = await axios.post(getBackendURL() + "/auth/logout", data, {
            headers: {
                "Content-Type": "application/json",
            }
        });
        window.location.href = "../login/login.html";
    } catch (err) {
        console.error(err);
    }
}