package repository;

import data.Book;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(UUID id);

    Optional<Book> findByTitle(String title);
}
