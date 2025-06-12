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
    public void shouldReturnAuthorWhenFound() {
        Book book = new Book(UUID.randomUUID(), "Book Title", "Desc");
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(AUTHOR));
        when(bookRepository.getAuthor(book)).thenReturn(AUTHOR_ID);
        when(authorResponseAdapter.adapt(AUTHOR)).thenReturn(AUTHOR_RESPONSE);

        AuthorResponse result = resolver.author(book);

        assertEquals(AUTHOR_RESPONSE, result);
    }


    //TODO add a test when the book repo fails?

    @Test
    public void shouldThrowWhenAuthorNotFound() {
        Book book = new Book(UUID.randomUUID(), "Another Book", "Desc");
        when(bookRepository.getAuthor(book)).thenReturn(AUTHOR_ID);
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.empty());

        AuthorNotFoundException exception = assertThrows(
            AuthorNotFoundException.class,
            () -> resolver.author(book)
        );

        assertEquals("Author not found: " + AUTHOR_ID, exception.getMessage());
    }
}
