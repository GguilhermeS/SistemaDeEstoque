package com.br.SistemaDeEstoques.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.SistemaDeEstoques.model.Produto;
import com.br.SistemaDeEstoques.model.User;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByUser(User user);

    List<Produto> findByUserId(Long userId);

    List<Produto> findByUserAndNomeContainingIgnoreCase(User user, String nome);

    List<Produto> findByUserAndNomeContainingIgnoreCaseOrTipoContainingIgnoreCase(@Param("user") User user, @Param("filtro") String filtroNome, @Param("filtro") String filtroTipo);

}
