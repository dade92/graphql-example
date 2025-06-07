package repository;

import data.Author;
import data.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Book save(Book book);

    Optional<Book> findById(UUID id);

    Optional<Book> findByTitle(String title);

    List<Book> findByAuthor(Author author);
}
