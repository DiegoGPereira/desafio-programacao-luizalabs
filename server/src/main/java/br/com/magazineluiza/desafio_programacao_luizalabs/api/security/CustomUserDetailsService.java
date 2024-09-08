package br.com.magazineluiza.desafio_programacao_luizalabs.api.security;

import br.com.magazineluiza.desafio_programacao_luizalabs.core.model.UserEntity;
import br.com.magazineluiza.desafio_programacao_luizalabs.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("usuário não encontrado: " + username));

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                user.getAuthorities()
        );
    }

}
