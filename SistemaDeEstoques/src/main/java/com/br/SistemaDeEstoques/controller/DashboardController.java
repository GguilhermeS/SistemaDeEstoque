package com.br.SistemaDeEstoques.controller;

import com.br.SistemaDeEstoques.model.Produto;
import com.br.SistemaDeEstoques.model.User;
import com.br.SistemaDeEstoques.service.DashboardService;
import com.br.SistemaDeEstoques.service.ProdutoService;
import com.br.SistemaDeEstoques.service.UserService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final ProdutoService produtoService;
    private final UserService userService;

    public DashboardController(DashboardService dashboardService, ProdutoService produtoService, UserService userService) {
        this.dashboardService = dashboardService;
        this.produtoService = produtoService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) String filtro,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        User usuario = userService.findByUsername(userDetails.getUsername()).orElseThrow();

        // Busca filtrada ou todos os produtos
        List<Produto> produtos;
        if (filtro != null && !filtro.isEmpty()) {
            produtos = produtoService.listarDoUsuario(usuario)
                    .stream()
                    .filter(p -> p.getNome().toLowerCase().contains(filtro.toLowerCase())
                    || p.getTipo().toLowerCase().contains(filtro.toLowerCase()))
                    .toList();
        } else {
            produtos = dashboardService.listarProdutos(usuario);
        }

        // Adiciona atributos ao modelo
        model.addAttribute("username", usuario.getUsername());
        model.addAttribute("totalProdutos", dashboardService.calcularTotalProdutos(usuario));
        model.addAttribute("totalEstoque", dashboardService.calcularTotalEstoque(usuario));
        model.addAttribute("valorTotal", dashboardService.calcularValorTotal(usuario));
        model.addAttribute("baixoEstoque", dashboardService.calcularBaixoEstoque(usuario));
        model.addAttribute("produtos", produtos);
        model.addAttribute("filtro", filtro);

        return "dashboard";
    }
}
