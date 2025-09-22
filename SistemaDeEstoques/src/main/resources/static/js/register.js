// register.js
document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");

    form.addEventListener("submit", (e) => {
        const username = form.querySelector("input[name='username']").value.trim();
        const email = form.querySelector("input[name='email']").value.trim();
        const password = form.querySelector("input[name='password']").value.trim();
        const confirm = form.querySelector("input[name='confirmPassword']").value.trim();

        if (!username || !email || !password || !confirm) {
            e.preventDefault();
            alert("Todos os campos são obrigatórios!");
            return;
        }

        if (password !== confirm) {
            e.preventDefault();
            alert("As senhas não coincidem!");
        }
    });
});
