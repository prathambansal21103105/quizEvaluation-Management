package com.quiz.layoutPDF.Controller;

import com.quiz.layoutPDF.models.Author;
import com.quiz.layoutPDF.Service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/emailFetch/{email}")
    public ResponseEntity<Author> getAuthorByEmail(@PathVariable String email) {
        Author savedAuthor = authorService.getAuthorByEmail(email);
        System.out.println(savedAuthor);
        if (savedAuthor != null) {
            return ResponseEntity.ok(savedAuthor);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Author savedAuthor = authorService.getAuthorById(id);
        System.out.println(savedAuthor);
        if(savedAuthor != null) {
            return ResponseEntity.ok(savedAuthor);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author savedAuthor = authorService.createAuthor(author);
        return ResponseEntity.ok(savedAuthor);
    }
}

