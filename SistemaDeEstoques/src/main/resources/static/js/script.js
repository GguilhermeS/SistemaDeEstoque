const API_URL = "http://localhost:8080/api/produtos";

async function deletarProduto(id) {
    if (confirm("Deseja excluir este produto?")) {
        await fetch(`${API_URL}/${id}`, { method: "DELETE" });
        alert("Produto excluído!");
        location.reload();
    }
}

async function editarProduto(id) {
    sessionStorage.setItem('produtoIdParaEditar', id);
    window.location.href = "form.html";
}

document.addEventListener("DOMContentLoaded", async () => {

    const form = document.getElementById("produtoForm");
    const tabela = document.getElementById("tabelaProdutos");

    if (form) {

        const produtoIdParaEditar = sessionStorage.getItem('produtoIdParaEditar');
        if (produtoIdParaEditar) {
            const response = await fetch(`${API_URL}/${produtoIdParaEditar}`);
            const produto = await response.json();

            document.getElementById("nome").value = produto.nome;
            document.getElementById("preco").value = produto.preco;
            document.getElementById("estoque").value = produto.estoque;
            document.getElementById("validade").value = produto.validade;
        }

        form.addEventListener("submit", async (e) => {
            e.preventDefault();

            const produtoId = sessionStorage.getItem('produtoIdParaEditar');
            const method = produtoId ? "PUT" : "POST";
            const url = produtoId ? `${API_URL}/${produtoId}` : API_URL;

            const produto = {
                nome: document.getElementById("nome").value,
                preco: parseFloat(document.getElementById("preco").value),
                estoque: parseInt(document.getElementById("estoque").value),
                validade: parseFloat(document.getElementById("validade").value),
            };

            await fetch(url, {
                method: method,
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(produto)
            });


            sessionStorage.removeItem('produtoIdParaEditar');
            alert(produtoId ? "Produto atualizado com sucesso!" : "Produto cadastrado com sucesso!");
            window.location.href = "lista.html";
        });
    }

    if (tabela) {
        try {
            const response = await fetch(API_URL);
            const produtos = await response.json();

            produtos.forEach(p => {
                let row = `
                    <tr>
                        <td>${p.id}</td>
                        <td>${p.nome}</td>
                        <td>R$ ${p.preco.toFixed(2)}</td>
                        <td>${p.estoque}</td>
                        <td>${p.validade.toFixed(0)}</td>
                        <td>
                            <button onclick="editarProduto(${p.id})">Editar</button>
                            <button onclick="deletarProduto(${p.id})">Excluir</button>
                        </td>
                    </tr>`;
                tabela.innerHTML += row;
            });
        } catch (error) {
            console.error('Erro ao buscar produtos:', error);
        }
    }
});