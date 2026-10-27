package com.example.mongoapp.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.mongoapp.model.Role;
import com.example.mongoapp.model.Usuario;
import com.example.mongoapp.repository.UsuarioRepository;

@Configuration
@Profile("dev")
public class DevDataSeeder {

    @Bean
    CommandLineRunner seedAdmin(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (!repo.existsByEmail("admin@exemplo.com")) {
                Usuario admin = new Usuario();
                admin.setNome("Admin");
                admin.setEmail("admin@exemplo.com");
                admin.setPassword(encoder.encode("Admin@12345"));
                admin.setRoles(Set.of(Role.USER, Role.ADMIN));
                repo.save(admin);
            }
        };
    }
}