package com.br.SistemaDeEstoques.controller;

import com.br.SistemaDeEstoques.model.User;
import com.br.SistemaDeEstoques.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class RecuperarSenhaController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RecuperarSenhaController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/esqueci-senha")
    public String exibirPaginaEsqueciSenha() {
        return "esqueci-senha";
    }

    @PostMapping("/verificar-email")
    public String verificarEmail(@RequestParam String email, Model model) {
        Optional<User> userOpt = userService.findByEmail(email);

        if (userOpt.isPresent()) {
            model.addAttribute("email", email);
            return "redefinir-senha";
        } else {
            model.addAttribute("mensagemErro", "E-mail não encontrado.");
            return "esqueci-senha";
        }
    }

    @PostMapping("/salvar-nova-senha")
    public String salvarNovaSenha(
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String confirmarSenha,
            Model model) {

        if (!senha.equals(confirmarSenha)) {
            model.addAttribute("mensagemErro", "As senhas não coincidem!");
            model.addAttribute("email", email);
            return "redefinir-senha";
        }

        Optional<User> userOpt = userService.findByEmail(email);
        if (userOpt.isPresent()) {
            User usuario = userOpt.get();
            usuario.setPassword(passwordEncoder.encode(senha));
            userService.salvar(usuario);

            model.addAttribute("mensagemSucesso", "Senha alterada com sucesso! Agora faça login.");
            return "login";
        } else {
            model.addAttribute("mensagemErro", "Erro ao salvar nova senha.");
            return "esqueci-senha";
        }
    }
}
