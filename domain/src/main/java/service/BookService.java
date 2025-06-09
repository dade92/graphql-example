package service;

import data.Author;
import data.Book;
import repository.AuthorRepository;
import repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book createBook(String title, String description, String authorName) {
        Optional<Author> author = authorRepository.findByName(authorName);

        if (author.isEmpty()) {
            throw new AuthorNotFoundException("Author not found: " + authorName);
        }
        Book book = new Book(UUID.randomUUID(), title, description, author.get().id());
        return bookRepository.save(book);
    }

    public Book findBookById(UUID id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElse(null);
    }

    public List<Book> findByAuthor(UUID authorId) {
        return bookRepository.findByAuthor(authorId);
    }

}
