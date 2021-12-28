package com.dvc.author.model;

import com.dvc.author.model.jpa.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "SELECT * FROM authors WHERE full_name = ?1", nativeQuery = true)
    Optional<Author> findByFullName(String fullName);
}
