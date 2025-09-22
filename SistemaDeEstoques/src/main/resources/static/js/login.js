// login.js
document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");

    form.addEventListener("submit", (e) => {
        const username = form.querySelector("input[name='username']").value.trim();
        const password = form.querySelector("input[name='password']").value.trim();

        if (!username || !password) {
            e.preventDefault();
            alert("Preencha todos os campos para continuar!");
        }
    });
});
