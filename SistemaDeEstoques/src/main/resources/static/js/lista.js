// dashboard.js
document.addEventListener("DOMContentLoaded", () => {
    const addForm = document.querySelector("#addProdutoForm");

    if (addForm) {
        addForm.addEventListener("submit", (e) => {
            const nome = addForm.querySelector("input[name='nome']").value.trim();
            const quantidade = addForm.querySelector("input[name='quantidade']").value.trim();

            if (!nome || !quantidade) {
                e.preventDefault();
                alert("Preencha todos os campos do produto!");
            }
        });
    }

    const deleteButtons = document.querySelectorAll(".btn-danger");
    deleteButtons.forEach(btn => {
        btn.addEventListener("click", (e) => {
            if (!confirm("Deseja realmente excluir este produto?")) {
                e.preventDefault();
            }
        });
    });
});
