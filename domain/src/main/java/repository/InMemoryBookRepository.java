package repository;

import data.Book;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {
    private final Map<UUID, Book> books = new HashMap<>();

    @Override
    public Book save(Book book) {
        books.put(book.id(), book);
        return book;
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return books.values().stream()
            .filter(book -> book.title().equalsIgnoreCase(title))
            .findFirst();
    }

    @Override
    public List<Book> findByAuthor(UUID authorId) {
        return books
            .values().stream()
            .filter(book -> book.authorId().equals(authorId))
            .collect(Collectors.toList());
    }

}
