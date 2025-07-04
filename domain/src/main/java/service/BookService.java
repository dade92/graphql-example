package service;

import model.Author;
import model.Book;
import provider.BookIdProvider;
import repository.AuthorRepository;
import repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookIdProvider bookIdProvider;

    public BookService(
        BookRepository bookRepository,
        AuthorRepository authorRepository,
        BookIdProvider bookIdProvider
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookIdProvider = bookIdProvider;
    }

    public Book createBook(String title, String description, String authorName) {
        Optional<Author> author = authorRepository.findByName(authorName);

        if (author.isEmpty()) {
            throw new AuthorNotFoundException("Author not found: " + authorName);
        }
        Book book = new Book(bookIdProvider.getBookId(), title, description);
        return bookRepository.save(book, author.get().id());
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
