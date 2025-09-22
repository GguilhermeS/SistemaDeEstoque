document.addEventListener("DOMContentLoaded", async () => {
    try {
        const response = await fetch("http://localhost:8080/api/produtos");
        const produtos = await response.json();

        let totalProdutos = produtos.length;
        let totalEstoque = 0;
        let valorTotal = 0;
        let baixoEstoque = 0;


        const tbody = document.getElementById("produtosTable");
        tbody.innerHTML = "";

        produtos.forEach(p => {
            totalEstoque += p.estoque;
            valorTotal += p.preco * p.estoque;
            if (p.estoque < 5) baixoEstoque++;

            let row = `
                <tr>
                    <td>${p.nome}</td>
                    <td>R$ ${p.preco.toFixed(2)}</td>
                    <td>${p.estoque}</td>
                    <td>${p.validade}</td>
                </tr>
            `;
            tbody.innerHTML += row;
        });

        document.getElementById("totalProdutos").innerText = totalProdutos;
        document.getElementById("totalEstoque").innerText = totalEstoque;
        document.getElementById("valorTotal").innerText = "R$ " + valorTotal.toFixed(2);
        document.getElementById("baixoEstoque").innerText = baixoEstoque;

    } catch (error) {
        console.error("Erro ao carregar produtos:", error);
    }
});
