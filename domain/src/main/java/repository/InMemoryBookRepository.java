package repository;

import model.Book;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

record BookDto(UUID id, String title, String description, UUID authorId) {
}

public class InMemoryBookRepository implements BookRepository {
    private final Map<UUID, BookDto> books = new HashMap<>();

    @Override
    public Book save(Book book, UUID id) {
        books.put(book.id(), new BookDto(book.id(), book.title(), book.description(), book.authorId()));
        return book;
    }

    @Override
    public Optional<Book> findById(UUID id) {
        BookDto bookDto = books.get(id);
        if (bookDto == null) return Optional.empty();
        else return Optional.of(new Book(bookDto.id(), bookDto.title(), bookDto.description(), bookDto.authorId()));
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        Optional<BookDto> bookDto = books.values().stream()
            .filter(book -> book.title().equalsIgnoreCase(title))
            .findFirst();
        return bookDto.map(dto -> new Book(dto.id(), dto.title(), dto.description(), dto.authorId()));
    }

    @Override
    public List<Book> findByAuthor(UUID authorId) {
        return books
            .values().stream()
            .filter(book -> book.authorId().equals(authorId))
            .map(bookDto -> new Book(bookDto.id(), bookDto.title(), bookDto.description(), bookDto.authorId()))
            .collect(Collectors.toList());
    }

}
