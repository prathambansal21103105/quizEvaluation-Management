package com.quiz.layoutPDF.Service;

import com.quiz.layoutPDF.Repository.AuthorRepository;
import com.quiz.layoutPDF.Repository.PlayerRepository;
import com.quiz.layoutPDF.models.Author;
import com.quiz.layoutPDF.models.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;
    private final AuthorRepository authorRepository;

    public CustomUserDetailsService(PlayerRepository playerRepository, AuthorRepository authorRepository) {
        this.playerRepository = playerRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Player> player = playerRepository.findByEmail(email);
        if (player.isPresent()) {
            return User.withUsername(player.get().getEmail())
                    .password(player.get().getPassword())
                    .roles("PLAYER")
                    .build();
        }

        Optional<Author> author = authorRepository.findByEmail(email);
        if (author.isPresent()) {
            return User.withUsername(author.get().getEmail())
                    .password(author.get().getPassword())
                    .roles("AUTHOR")
                    .build();
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
