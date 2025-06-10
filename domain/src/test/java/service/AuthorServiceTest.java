package service;

import data.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import provider.AuthorIdProvider;
import repository.AuthorRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    public static final String NAME = "John";
    public static final String DATE_OF_BIRTH_INPUT = "15/06/1980";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(1980, 6, 15);

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private AuthorIdProvider authorIdProvider;

    private AuthorService authorService;

    @BeforeEach
    public void setUp() {
        authorService = new AuthorService(authorRepository, authorIdProvider);
    }

    @Test
    public void createAuthor() {
        UUID generatedId = UUID.randomUUID();
        Author author = new Author(generatedId, NAME, DATE_OF_BIRTH);

        when(authorRepository.findByName(NAME)).thenReturn(Optional.empty());
        when(authorIdProvider.getBookId()).thenReturn(generatedId);
        when(authorRepository.save(author)).thenReturn(author);

        Author result = authorService.createAuthor(NAME, DATE_OF_BIRTH_INPUT);

        assertEquals(author, result);
    }

    @Test
    public void createAuthorButAlreadyExists() {
        Author existingAuthor = new Author(UUID.randomUUID(), NAME, DATE_OF_BIRTH);

        when(authorRepository.findByName(NAME)).thenReturn(Optional.of(existingAuthor));

        ExistingAuthorException exception = assertThrows(
            ExistingAuthorException.class,
            () -> authorService.createAuthor(NAME, DATE_OF_BIRTH_INPUT)
        );

        assertEquals("Author named John already exists", exception.getMessage());

        verify(authorRepository, never()).save(any());
        verifyNoInteractions(authorIdProvider);
    }

    @Test
    public void getById() {
        UUID id = UUID.randomUUID();
        Author author = new Author(id, NAME, DATE_OF_BIRTH);
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorById(id);

        assertEquals(author, result);
    }

    @Test
    public void getAuthorByIdNotFound() {
        UUID id = UUID.randomUUID();
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Author result = authorService.getAuthorById(id);

        assertNull(result);
    }

    @Test
    public void getByNameHappyPath() {
        Author author = new Author(UUID.randomUUID(), NAME, DATE_OF_BIRTH);
        when(authorRepository.findByName(NAME)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorByName(NAME);

        assertEquals(author, result);
    }

    @Test
    public void getAuthorByNameNotFound() {
        String name = "Unknown";
        when(authorRepository.findByName(name)).thenReturn(Optional.empty());

        Author result = authorService.getAuthorByName(name);

        assertNull(result);
    }
}
