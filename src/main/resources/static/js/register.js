document.getElementById("registerForm")
    .addEventListener("submit", async function (e) {

        e.preventDefault();

        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        const response = await fetch("/api/users/register", {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                name: name,
                email: email,
                password: password
            })

        });

        if (response.ok) {

            alert("Registration Successful!");

            window.location.href = "/";

        } else {

            alert("Registration Failed!");

        }

    });