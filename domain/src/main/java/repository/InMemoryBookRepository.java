package repository;

import data.Author;
import data.Book;

import java.util.*;

public class InMemoryBookRepository implements BookRepository {
    private final Map<UUID, Book> books = new HashMap<>();

    @Override
    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return books.values().stream()
            .filter(book -> book.getTitle().equalsIgnoreCase(title))
            .findFirst();
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return Collections.emptyList();
    }

}
