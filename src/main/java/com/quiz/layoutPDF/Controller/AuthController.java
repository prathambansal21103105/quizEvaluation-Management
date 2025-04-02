package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.Repository.AuthorRepository;
import com.quiz.layoutPDF.Repository.PlayerRepository;
import com.quiz.layoutPDF.models.Author;
import com.quiz.layoutPDF.Config.JwtUtil;
import com.quiz.layoutPDF.models.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthorRepository authorRepository;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthorRepository authorRepository, PlayerRepository playerRepository,
                          PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
                          AuthenticationManager authenticationManager) {
        this.authorRepository = authorRepository;
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register/author")
    public ResponseEntity<String> registerAuthor(@RequestBody Author author) {
        if (authorRepository.findByEmail(author.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Author with this email already exists.");
        }
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        authorRepository.save(author);
        return ResponseEntity.ok("Author registered successfully!");
    }

    @PostMapping("/register/player")
    public ResponseEntity<String> registerPlayer(@RequestBody Player player) {
        if (playerRepository.findByEmail(player.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Player with this email already exists.");
        }

        if (playerRepository.findById(player.getId()).isPresent()) {
            return ResponseEntity.badRequest().body("Player with this ID already exists.");
        }

        player.setPassword(passwordEncoder.encode(player.getPassword()));
        playerRepository.save(player);
        return ResponseEntity.ok("Player registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<Author> author = authorRepository.findByEmail(email);
        Optional<Player> player = playerRepository.findByEmail(email);
        if (author.isPresent() && passwordEncoder.matches(password, author.get().getPassword())) {
            String token = jwtUtil.generateToken(author.get().getEmail());
            return ResponseEntity.ok("JWT Token: " + token);
        }

        if (player.isPresent() && passwordEncoder.matches(password, player.get().getPassword())) {
            String token = jwtUtil.generateToken(player.get().getEmail());
            return ResponseEntity.ok("JWT Token: " + token);
        }

        return ResponseEntity.status(401).body("Invalid email or password.");
    }

}
