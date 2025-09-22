package com.br.SistemaDeEstoques.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.br.SistemaDeEstoques.model.Produto;
import com.br.SistemaDeEstoques.model.User;
import com.br.SistemaDeEstoques.service.ProdutoService;
import com.br.SistemaDeEstoques.service.UserService;

@Controller
public class ProdutoController {

    private final ProdutoService produtos;
    private final UserService users;

    public ProdutoController(ProdutoService produtos, UserService users) {
        this.produtos = produtos;
        this.users = users;
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model, @AuthenticationPrincipal UserDetails auth) {
        User u = users.findByUsername(auth.getUsername()).orElseThrow();
        model.addAttribute("novoProduto", new Produto());
        model.addAttribute("username", u.getUsername());
        return "cadastro";
    }

    @PostMapping("/produtos")
    public String adicionar(@ModelAttribute("novoProduto") Produto p,
            @AuthenticationPrincipal UserDetails auth) {
        User u = users.findByUsername(auth.getUsername()).orElseThrow();
        p.setUsuario(u); 
        p.setDataRegistro(java.time.LocalDate.now());
        produtos.salvarParaUsuario(p, u);
        return "redirect:/dashboard";
    }

    @PostMapping("/produtos/{id}/excluir")
    public String excluir(@PathVariable Long id,
            @AuthenticationPrincipal UserDetails auth) {
        User u = users.findByUsername(auth.getUsername()).orElseThrow();
        produtos.excluirDoUsuario(id, u);
        return "redirect:/dashboard";
    }

    @GetMapping("/lista")
    public String lista(@AuthenticationPrincipal UserDetails auth, Model model) {
        User u = users.findByUsername(auth.getUsername()).orElseThrow();
        model.addAttribute("produtos", produtos.listarDoUsuario(u));
        model.addAttribute("username", u.getUsername());
        return "lista";
    }

    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id,
            @AuthenticationPrincipal UserDetails auth,
            Model model) {
        User u = users.findByUsername(auth.getUsername()).orElseThrow();
        Produto produto = produtos.buscarDoUsuario(id, u);

        if (produto == null) {
            return "redirect:/dashboard?error=Produto não encontrado";
        }

        model.addAttribute("produto", produto);
        model.addAttribute("username", u.getUsername());
        return "editar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarProduto(@PathVariable Long id,
            @ModelAttribute Produto produtoAtualizado,
            @AuthenticationPrincipal UserDetails auth) {
        User u = users.findByUsername(auth.getUsername()).orElseThrow();
        produtos.atualizarDoUsuario(id, produtoAtualizado, u);
        return "redirect:/lista";
    }
}
