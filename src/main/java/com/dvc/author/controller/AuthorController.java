package com.dvc.author.controller;

import com.dvc.author.controller.dto.AuthorPostDto;
import com.dvc.author.controller.dto.AuthorPutDto;
import com.dvc.author.model.jpa.Author;
import com.dvc.author.service.impl.AuthorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/authors")
@RestController
public class AuthorController {

    private final AuthorServiceImpl authorService;

    @GetMapping
    public ResponseEntity<List<Author>> index() {
        final List<Author> authors = authorService.fetchAllAuthors();

        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity showById(@PathVariable long id) {
        try {
            final Author author = authorService.fetchAuthorById(id);

            return ResponseEntity.ok(author);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity showByName(@RequestParam String fn) {
        try {
            final Author author = authorService.fetchAuthorByName(fn);
            return ResponseEntity.ok(author);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody AuthorPostDto author) {

        final String fullName = author.getFullName();
        final Optional<Date> birthday = Optional.ofNullable(author.getBirthday()).map(Date::valueOf);
        final Optional<Date> deathday = Optional.ofNullable(author.getDeathday()).map(Date::valueOf);
        final Optional<String> bio = Optional.ofNullable(author.getBio());
        final Optional<String> portraitPic = Optional.ofNullable(author.getPortraitPic());
        final Optional<String> pseudoname = Optional.ofNullable(author.getPseudoname());

        try {
            final long createdAuthorId = authorService.createAuthor(fullName, birthday, deathday, bio, portraitPic, pseudoname);
            final String authorUri = String.format("/authors/%d", createdAuthorId);

            return ResponseEntity.created(URI.create(authorUri)).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody AuthorPutDto author) {

        final Optional<String> fullName = Optional.ofNullable(author.getFullName());
        final Optional<Date> birthday = Optional.ofNullable(author.getBirthday()).map(Date::valueOf);
        final Optional<Date> deathday = Optional.ofNullable(author.getDeathday()).map(Date::valueOf);
        final Optional<String> bio = Optional.ofNullable(author.getBio());
        final Optional<String> portraitPic = Optional.ofNullable(author.getPortraitPic());
        final Optional<String> pseudoname = Optional.ofNullable(author.getPseudoname());

        try {
            final Author createdAuthorId = authorService.fetchAuthorById(id);
            authorService.updateAuthor(id, fullName, birthday, deathday, bio, portraitPic, pseudoname);

            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable long id) {
        try {
            authorService.deleteAuthorById(id);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        return ResponseEntity.noContent().build();
    }
}
