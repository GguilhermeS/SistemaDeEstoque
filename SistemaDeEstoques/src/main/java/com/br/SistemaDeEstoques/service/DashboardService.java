package com.br.SistemaDeEstoques.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.br.SistemaDeEstoques.model.Produto;
import com.br.SistemaDeEstoques.model.User;
import com.br.SistemaDeEstoques.repository.ProdutoRepository;

@Service
public class DashboardService {

    private final ProdutoRepository produtoRepository;

    public DashboardService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarProdutos(User usuario) {
        return produtoRepository.findByUser(usuario);
    }

    public long calcularTotalProdutos(User usuario) {
        return produtoRepository.findByUser(usuario).size();
    }

    public int calcularTotalEstoque(User usuario) {
        return produtoRepository.findByUser(usuario)
                .stream()
                .mapToInt(Produto::getQuantidade)
                .sum();
    }

    public BigDecimal calcularValorTotal(User usuario) {
        return produtoRepository.findByUser(usuario)
                .stream()
                .filter(p -> p.getPreco() != null && p.getQuantidade() != null)
                .map(p -> p.getPreco().multiply(BigDecimal.valueOf(p.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long calcularBaixoEstoque(User usuario) {
        return produtoRepository.findByUser(usuario)
                .stream()
                .filter(p -> p.getQuantidade() < 5)
                .count();
    }
}
