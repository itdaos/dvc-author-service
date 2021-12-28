package com.dvc.author.service;

import com.dvc.author.model.jpa.Author;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service("authorService")
public interface AuthorService {
    List<Author> fetchAllAuthors();
    Author fetchAuthorById(long id) throws IllegalArgumentException;
    Author fetchAuthorByName(String name) throws IllegalArgumentException;
    long createAuthor(String full_name, Optional<Date> maybeBirthday, Optional<Date> maybeDeathday, Optional<String> maybeBio, Optional<String> maybePortraitPic, Optional<String> maybePseudoname) throws IllegalArgumentException ;

    void deleteAuthorById(long id) throws IllegalArgumentException;
    void updateAuthor(long id, Optional<String> maybeFullName, Optional<Date> maybeBirthday, Optional<Date> maybeDeathday, Optional<String> maybeBio, Optional<String> maybePortraitPic, Optional<String> maybePseudoname);
}
