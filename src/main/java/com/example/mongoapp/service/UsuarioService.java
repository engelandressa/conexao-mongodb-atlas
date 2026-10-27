package com.example.mongoapp.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mongoapp.auth.RegisterRequest;
import com.example.mongoapp.model.Role;
import com.example.mongoapp.model.Usuario;
import com.example.mongoapp.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();

        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.name().trim());
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRoles(Set.of(Role.USER));

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(String id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());

            return usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
    }

    public void deletar(String id) {
        usuarioRepository.deleteById(id);
    }
}