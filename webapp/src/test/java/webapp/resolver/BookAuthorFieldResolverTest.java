package webapp.resolver;

import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.AuthorRepository;
import service.AuthorNotFoundException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookAuthorFieldResolverTest {

    public static final UUID AUTHOR_ID = UUID.randomUUID();
    public static final Author AUTHOR = new Author(AUTHOR_ID, "Jane", LocalDate.of(1975, 8, 20));

    @Mock
    private AuthorRepository authorRepository;

    private BookAuthorFieldResolver resolver;

    @BeforeEach
    public void setUp() {
        resolver = new BookAuthorFieldResolver(authorRepository);
    }

    @Test
    public void shouldReturnAuthorWhenFound() {
        Book book = new Book(UUID.randomUUID(), "Book Title", "Desc", AUTHOR_ID);
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(AUTHOR));

        Author result = resolver.author(book);

        assertEquals(AUTHOR, result);
    }

    @Test
    public void shouldThrowWhenAuthorNotFound() {
        Book book = new Book(UUID.randomUUID(), "Another Book", "Desc", AUTHOR_ID);
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.empty());

        AuthorNotFoundException exception = assertThrows(
            AuthorNotFoundException.class,
            () -> resolver.author(book)
        );

        assertEquals("Author not found: " + AUTHOR_ID, exception.getMessage());
    }
}
