package br.com.magazineluiza.desafio_programacao_luizalabs.api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final List<CustomUserDetails> users = new ArrayList<>();

    public CustomUserDetailsService() {
        users.add(new CustomUserDetails("user1", "$2a$12$74qc9RB/33wyGlvnic3bu.5SYYTnKnFpDf.lC7ZRwaHOAAnuLui7K", true, "ROLE_USER"));
        users.add(new CustomUserDetails("admin", "$2a$12$74qc9RB/33wyGlvnic3bu.5SYYTnKnFpDf.lC7ZRwaHOAAnuLui7K", true, "ROLE_ADMIN"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado: " + username));
    }
}
