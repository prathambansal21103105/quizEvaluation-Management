package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.Repository.AuthorRepository;
import com.quiz.layoutPDF.Repository.PlayerRepository;
import com.quiz.layoutPDF.models.Author;
import com.quiz.layoutPDF.models.JwtUtil;
import com.quiz.layoutPDF.models.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
        author.setPassword(passwordEncoder.encode(author.getPassword())); // Hash password
        authorRepository.save(author);
        return ResponseEntity.ok("Author registered successfully!");
    }

    @PostMapping("/register/player")
    public ResponseEntity<String> registerPlayer(@RequestBody Player player) {
        player.setPassword(passwordEncoder.encode(player.getPassword())); // Hash password
        playerRepository.save(player);
        return ResponseEntity.ok("Player registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<Author> author = authorRepository.findByEmail(email);
        Optional<Player> player = playerRepository.findByEmail(email);

//        if (author.isPresent()) {
//            return authenticateUser(author.get().getEmail(), password);
//        } else if (player.isPresent()) {
//            return authenticateUser(player.get().getEmail(), password);
//        } else {
//            return ResponseEntity.status(401).body("Invalid email or password.");
//        }
        if(author.isPresent()){
            if(passwordEncoder.matches(password, author.get().getPassword())){
                return ResponseEntity.ok("Login successful for author!");
            }
        }
        if(player.isPresent()){
            if(passwordEncoder.matches(password, player.get().getPassword())){
                return ResponseEntity.ok("Login successful for player!");
            }
        }
        return ResponseEntity.ok("Login failed!");
    }

    private ResponseEntity<String> authenticateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        String token = jwtUtil.generateToken(email);
        return ResponseEntity.ok("JWT Token: " + token);
    }
}
