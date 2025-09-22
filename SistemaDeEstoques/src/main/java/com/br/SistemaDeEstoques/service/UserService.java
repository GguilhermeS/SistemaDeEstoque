package com.br.SistemaDeEstoques.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.SistemaDeEstoques.model.User;
import com.br.SistemaDeEstoques.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username é obrigatório");
        }
        if (users.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username já existe");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole("USER");
        }
        return users.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return users.findByUsername(username);
    }

    public User findById(Long id) {
        return users.findById(id).orElse(null);
    }

    public Optional<User> findByEmail(String email) {
        return users.findByEmail(email);
    }

    public void salvar(User user) {
        users.save(user);
    }

    public void atualizarSenha(User user, String novaSenha) {
        user.setPassword(passwordEncoder.encode(novaSenha));
        users.save(user);
    }
}
