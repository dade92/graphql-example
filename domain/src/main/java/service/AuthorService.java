package service;

import data.Author;
import provider.AuthorIdProvider;
import repository.AuthorRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorIdProvider authorIdProvider;

    public AuthorService(
        AuthorRepository authorRepository,
        AuthorIdProvider authorIdProvider
    ) {
        this.authorRepository = authorRepository;
        this.authorIdProvider = authorIdProvider;
    }

    public Author createAuthor(String name, String surname, String dateOfBirth) {
        Optional<Author> existingAuthor = authorRepository.findByName(name);
        if (existingAuthor.isPresent()) {
            throw new ExistingAuthorException("Author named " + name + " already exists");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateOfBirth, formatter);
        Author author = new Author(authorIdProvider.getBookId(), name, surname, date);

        return authorRepository.save(author);
    }

    public Author getAuthorById(UUID id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name).orElse(null);
    }

}
