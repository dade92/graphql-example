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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

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
        String name = "John";
        String surname = "Doe";
        String dob = "15/06/1980";
        UUID generatedId = UUID.randomUUID();
        Author author = new Author(generatedId, name, surname, LocalDate.of(1980, 6, 15));

        when(authorRepository.findByName(name)).thenReturn(Optional.empty());
        when(authorIdProvider.getBookId()).thenReturn(generatedId);

        when(authorRepository.save(author)).thenReturn(author);

        Author result = authorService.createAuthor(name, surname, dob);

        verify(authorRepository).findByName(name);
        verify(authorIdProvider).getBookId();
        verify(authorRepository).save(author);

        assertEquals(author, result);
    }

    @Test
    public void createAuthorButAlreadyExists() {
        String name = "Jane";
        String surname = "Smith";
        String dob = "01/01/1990";
        Author existing = new Author(UUID.randomUUID(), name, surname, LocalDate.of(1990, 1, 1));

        when(authorRepository.findByName(name)).thenReturn(Optional.of(existing));

        ExistingAuthorException exception = assertThrows(
            ExistingAuthorException.class,
            () -> authorService.createAuthor(name, surname, dob)
        );

        assertEquals("Author named Jane already exists", exception.getMessage());

        verify(authorRepository).findByName(name);
        verify(authorRepository, never()).save(any());
        verifyNoInteractions(authorIdProvider);
    }

    @Test
    public void getById() {
        UUID id = UUID.randomUUID();
        Author author = new Author(id, "name", "surname", LocalDate.of(2000, 1, 1));
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
        String name = "Emily";
        Author author = new Author(UUID.randomUUID(), name, "Bronte", LocalDate.of(1818, 7, 30));
        when(authorRepository.findByName(name)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorByName(name);

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
