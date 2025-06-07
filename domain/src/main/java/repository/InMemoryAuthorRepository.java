package repository;

import data.Author;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryAuthorRepository implements AuthorRepository {
    private final Map<UUID, Author> authors = new HashMap<>();

    @Override
    public Author save(Author author) {
        authors.put(author.getId(), author);
        return author;
    }

    @Override
    public Optional<Author> findById(UUID id) {
        return Optional.ofNullable(authors.get(id));
    }

    @Override
    public Optional<Author> findByName(String name) {
        return authors.values().stream()
            .filter(author -> author.getName().equalsIgnoreCase(name))
            .findFirst();
    }
}

