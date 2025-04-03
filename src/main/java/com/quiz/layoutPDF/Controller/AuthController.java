package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.Repository.AuthorRepository;
import com.quiz.layoutPDF.Repository.PlayerRepository;
import com.quiz.layoutPDF.models.Author;
import com.quiz.layoutPDF.Config.JwtUtil;
import com.quiz.layoutPDF.models.Player;
import com.quiz.layoutPDF.models.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
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
    public ResponseEntity<Map<String, Object>> loginUser(@RequestParam String email, @RequestParam String password) {
        Optional<Author> author = authorRepository.findByEmail(email);
        Optional<Player> player = playerRepository.findByEmail(email);

        Map<String, Object> response = new HashMap<>();

        if (author.isPresent() && passwordEncoder.matches(password, author.get().getPassword())) {
            String token = jwtUtil.generateToken(email, Role.AUTHOR);
            response.put("message", token);
            response.put("flag", 1);
            return ResponseEntity.ok(response);
        } else if (player.isPresent() && passwordEncoder.matches(password, player.get().getPassword())) {
            String token = jwtUtil.generateToken(email, Role.PLAYER);
            response.put("message", token);
            response.put("flag", 0);
            return ResponseEntity.ok(response);
        }

        response.put("flag", 2);
        response.put("message", "Invalid credentials");
        return ResponseEntity.status(401).body(response);
    }

    @PostMapping("/login/password")
    public ResponseEntity<String> setPassword(@RequestParam String email, @RequestParam String password) {
        Optional<Author> author = authorRepository.findByEmail(email);
        Optional<Player> player = playerRepository.findByEmail(email);


        if (author.isPresent()) {
            Author savedAuthor = author.get();
            savedAuthor.setPassword(passwordEncoder.encode(password));
            authorRepository.save(savedAuthor);
            return ResponseEntity.ok("Password changed successfully for author!");
        } else if (player.isPresent()) {
            Player savedPlayer = player.get();
            savedPlayer.setPassword(passwordEncoder.encode(password));
            playerRepository.save(savedPlayer);
            return ResponseEntity.ok("Password changed successfully for student!");
        }

        return ResponseEntity.status(401).body("Invalid email!");
    }
}
