package repository;

import model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {
    Book save(Book book, UUID authorId);

    Optional<Book> findById(UUID id);

    Optional<Book> findByTitle(String title);

    List<Book> findByAuthor(UUID author);

    UUID getAuthor(Book book);
}
