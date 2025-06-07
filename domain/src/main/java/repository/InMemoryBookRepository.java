package repository;

import data.Book;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBookRepository implements BookRepository {
    private final Map<UUID, Book> books = new ConcurrentHashMap<>();

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
}
