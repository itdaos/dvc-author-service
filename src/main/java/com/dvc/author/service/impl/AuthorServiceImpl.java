package com.dvc.author.service.impl;

import com.dvc.author.model.jpa.Author;
import com.dvc.author.model.AuthorRepository;
import com.dvc.author.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepo;

    @Override
    public List<Author> fetchAllAuthors() {
        return authorRepo.findAll();
    }

    @Override
    public Author fetchAuthorById(long id) throws IllegalArgumentException {
        final Optional<Author> maybeAuthor = authorRepo.findById(id);

        if (maybeAuthor.isPresent()) {
            return maybeAuthor.get();
        } else {
            throw new IllegalArgumentException("The author with such id was not found");
        }
    }

    @Override
    public Author fetchAuthorByName(String name) throws IllegalArgumentException {
        final Optional<Author> maybeAuthor = authorRepo.findByFullName(name);

        if (maybeAuthor.isPresent()) {
            return maybeAuthor.get();
        } else {
            throw new IllegalArgumentException("The author with such name was not found");
        }
    }

    @Override
    public long createAuthor(
            String full_name,
            Optional<Date> maybeBirthday,
            Optional<Date> maybeDeathday,
            Optional<String> maybeBio,
            Optional<String> maybePortraitPic,
            Optional<String> maybePseudoname
    ) throws IllegalArgumentException {
        final Author author = new Author(full_name);

        // TODO: need to clean this mess
        maybeBio.ifPresent(author::setBio);
        maybeBirthday.ifPresent(author::setBirthday);
        maybePortraitPic.ifPresent(author::setPortraitPic);
        maybeDeathday.ifPresent(author::setDeathday);
        maybePseudoname.ifPresent(author::setPseudoname);

        final Optional<Author> maybeAuthor = authorRepo.findByFullName(author.getFullName());

        if (maybeAuthor.isPresent()) throw new IllegalArgumentException("The author with such name already exists");

        final Author savedAuthor = authorRepo.save(author);

        return savedAuthor.getId();
    }

    @Override
    public void deleteAuthorById(long id) throws IllegalArgumentException {
        Optional<Author> maybeAuthor = authorRepo.findById(id);
        if (maybeAuthor.isEmpty()) throw new IllegalArgumentException("The author with such id is not present");

        authorRepo.deleteById(id);
    }

    @Override
    public void updateAuthor(
            long id,
            Optional<String> maybeFullName,
            Optional<Date> maybeBirthday,
            Optional<Date> maybeDeathday,
            Optional<String> maybeBio,
            Optional<String> maybePortraitPic,
            Optional<String> maybePseudoname
    ) throws IllegalArgumentException {
        Optional<Author> maybeAuthor = authorRepo.findById(id);

        if (maybeAuthor.isEmpty()) throw new IllegalArgumentException("The author with such id was not found");

        final Author author = maybeAuthor.get();

        // TODO: need to clean this mess
        maybeBio.ifPresent(author::setBio);
        maybeBirthday.ifPresent(author::setBirthday);
        maybeFullName.ifPresent(author::setFullName);
        maybePortraitPic.ifPresent(author::setPortraitPic);
        maybeDeathday.ifPresent(author::setDeathday);
        maybePseudoname.ifPresent(author::setPseudoname);

        authorRepo.save(author);
    }
}
