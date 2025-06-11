package service;

import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import provider.BookIdProvider;
import repository.AuthorRepository;
import repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    private static final UUID AUTHOR_ID = UUID.randomUUID();
    private static final UUID BOOK_ID = UUID.randomUUID();
    private static final String TITLE = "Test Book";
    private static final String DESCRIPTION = "A test book description";
    private static final String AUTHOR_NAME = "John Doe";

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookIdProvider bookIdProvider;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepository, authorRepository, bookIdProvider);
    }

    @Test
    public void createBook() {
        Author author = new Author(AUTHOR_ID, AUTHOR_NAME, null);
        Book expectedBook = new Book(BOOK_ID, TITLE, DESCRIPTION, AUTHOR_ID);

        when(authorRepository.findByName(AUTHOR_NAME)).thenReturn(Optional.of(author));
        when(bookIdProvider.getBookId()).thenReturn(BOOK_ID);
        when(bookRepository.save(expectedBook, AUTHOR_ID)).thenReturn(expectedBook);

        Book result = bookService.createBook(TITLE, DESCRIPTION, AUTHOR_NAME);

        assertEquals(expectedBook, result);
    }

    @Test
    public void createBook_authorNotFound() {
        when(authorRepository.findByName(AUTHOR_NAME)).thenReturn(Optional.empty());

        AuthorNotFoundException exception = assertThrows(
            AuthorNotFoundException.class,
            () -> bookService.createBook(TITLE, DESCRIPTION, AUTHOR_NAME)
        );

        assertEquals("Author not found: " + AUTHOR_NAME, exception.getMessage());
    }

    @Test
    public void findBookById_found() {
        Book book = new Book(BOOK_ID, TITLE, DESCRIPTION, AUTHOR_ID);
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));

        Book result = bookService.findBookById(BOOK_ID);

        assertEquals(book, result);
    }

    @Test
    public void findBookById_notFound() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.empty());

        Book result = bookService.findBookById(BOOK_ID);

        assertNull(result);
    }

    @Test
    public void findBookByTitle_found() {
        Book book = new Book(BOOK_ID, TITLE, DESCRIPTION, AUTHOR_ID);
        when(bookRepository.findByTitle(TITLE)).thenReturn(Optional.of(book));

        Book result = bookService.findBookByTitle(TITLE);

        assertEquals(book, result);
    }

    @Test
    public void findBookByTitle_notFound() {
        when(bookRepository.findByTitle(TITLE)).thenReturn(Optional.empty());

        Book result = bookService.findBookByTitle(TITLE);

        assertNull(result);
    }

    @Test
    public void findByAuthor_returnsBooks() {
        Book book1 = new Book(UUID.randomUUID(), "Book 1", "Desc 1", AUTHOR_ID);
        Book book2 = new Book(UUID.randomUUID(), "Book 2", "Desc 2", AUTHOR_ID);
        List<Book> books = List.of(book1, book2);

        when(bookRepository.findByAuthor(AUTHOR_ID)).thenReturn(books);

        List<Book> result = bookService.findByAuthor(AUTHOR_ID);

        assertEquals(books, result);
    }
}
