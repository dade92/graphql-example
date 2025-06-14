package webapp.resolver;

import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.AuthorRepository;
import repository.BookRepository;
import service.AuthorNotFoundException;
import webapp.adapter.AuthorResponse;
import webapp.adapter.AuthorResponseAdapter;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookAuthorResolverTest {

    public static final UUID AUTHOR_ID = UUID.randomUUID();
    public static final Author AUTHOR = new Author(AUTHOR_ID, "Jane", LocalDate.of(1975, 8, 20));
    public static final AuthorResponse AUTHOR_RESPONSE = new AuthorResponse(AUTHOR_ID, "Jane", "20/08/1975");
    private static final Book BOOK = new Book(UUID.randomUUID(), "Book Title", "Desc");

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorResponseAdapter authorResponseAdapter;

    private BookAuthorResolver resolver;

    @BeforeEach
    public void setUp() {
        resolver = new BookAuthorResolver(authorRepository, bookRepository, authorResponseAdapter);
    }

    @Test
    public void resolveAuthor() {
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(AUTHOR));
        when(bookRepository.getAuthor(BOOK)).thenReturn(AUTHOR_ID);
        when(authorResponseAdapter.adapt(AUTHOR)).thenReturn(AUTHOR_RESPONSE);

        AuthorResponse result = resolver.author(BOOK);

        assertEquals(AUTHOR_RESPONSE, result);
    }

    @Test
    void authorIdNotFound() {
        when(bookRepository.getAuthor(BOOK)).thenReturn(null);

        AuthorNotFoundException exception = assertThrows(
            AuthorNotFoundException.class,
            () -> resolver.author(BOOK)
        );

        assertEquals("Author not found: null", exception.getMessage());
    }

    @Test
    public void authorNotFound() {
        when(bookRepository.getAuthor(BOOK)).thenReturn(AUTHOR_ID);
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.empty());

        AuthorNotFoundException exception = assertThrows(
            AuthorNotFoundException.class,
            () -> resolver.author(BOOK)
        );

        assertEquals("Author not found: " + AUTHOR_ID, exception.getMessage());
    }
}
