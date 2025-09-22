package com.br.SistemaDeEstoques.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.SistemaDeEstoques.model.Produto;
import com.br.SistemaDeEstoques.model.User;
import com.br.SistemaDeEstoques.repository.ProdutoRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarDoUsuario(User user) {
        return produtoRepository.findByUser(user);
    }

    public Produto salvarParaUsuario(Produto p, User user) {
        p.setUser(user);
        return produtoRepository.save(p);
    }

    public void excluirDoUsuario(Long id, User user) {
        Produto p = buscarDoUsuario(id, user);
        produtoRepository.delete(p);
    }

    public Produto buscarDoUsuario(Long id, User u) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        if (!produto.getUser().getId().equals(u.getId())) {
            throw new SecurityException("Produto não pertence ao usuário logado");
        }
        return produto;
    }

    public List<Produto> listarBaixoEstoque(User user) {
        return produtoRepository.findByUser(user)
                .stream()
                .filter(p -> p.getQuantidade() < 5)
                .toList();
    }

    public List<Produto> buscarPorNomeOuTipo(String filtro, User user) {
        return produtoRepository.findByUserAndNomeContainingIgnoreCaseOrTipoContainingIgnoreCase(user, filtro, filtro);
    }

    public void atualizarDoUsuario(Long id, Produto novo, User u) {
        Produto produto = buscarDoUsuario(id, u);
        produto.setNome(novo.getNome());
        produto.setQuantidade(novo.getQuantidade());
        produto.setValidade(novo.getValidade());
        produto.setDescricao(novo.getDescricao());
        produto.setPreco(novo.getPreco());
        produto.setPeso(novo.getPeso());
        produto.setTipo(novo.getTipo());

        produtoRepository.save(produto);
    }
}
