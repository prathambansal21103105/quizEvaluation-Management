package com.quiz.layoutPDF.Service;

import com.quiz.layoutPDF.Repository.AuthorRepository;
import com.quiz.layoutPDF.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
//    private final PasswordEncoder passwordEncoder;

//    @Autowired
//    public AuthorService(AuthorRepository authorRepository, PasswordEncoder passwordEncoder) {
//        this.authorRepository = authorRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(Author author) {
        // Encrypt password before saving
//        author.setPassword(passwordEncoder.encode(author.getPassword()));
        return authorRepository.save(author);
    }

    public Author getAuthorById(Long id) {
        Optional<Author> author = authorRepository.findById(id);
        System.out.println(author.get());
        return author.orElse(null);
    }
}
