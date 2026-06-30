document.getElementById("loginForm").addEventListener("submit", async function (event) {

    event.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/api/users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    });

    if (response.ok) {

        const user = await response.json();

        localStorage.setItem("userId", user.id);
        localStorage.setItem("userName", user.name);

        alert("Login Successful");

        // Dashboard page will be created next
        window.location.href = "/dashboard";

    } else {

        alert("Invalid Email or Password");

    }

});