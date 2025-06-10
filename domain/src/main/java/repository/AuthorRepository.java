package repository;

import model.Author;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository {
    Author save(Author author);

    Optional<Author> findById(UUID id);

    Optional<Author> findByName(String name);
}
