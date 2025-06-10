package service;

import model.Author;
import provider.AuthorIdProvider;
import repository.AuthorRepository;
import utils.AuthorNameNormalizer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorIdProvider authorIdProvider;
    private final AuthorNameNormalizer authorNameNormalizer;

    public AuthorService(
        AuthorRepository authorRepository,
        AuthorIdProvider authorIdProvider,
        AuthorNameNormalizer authorNameNormalizer
    ) {
        this.authorRepository = authorRepository;
        this.authorIdProvider = authorIdProvider;
        this.authorNameNormalizer = authorNameNormalizer;
    }

    public Author createAuthor(String name, String dateOfBirth) {
        String authorName = authorNameNormalizer.get(name);
        Optional<Author> existingAuthor = authorRepository.findByName(authorName);
        if (existingAuthor.isPresent()) {
            throw new ExistingAuthorException("Author named " + authorName + " already exists");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateOfBirth, formatter);
        Author author = new Author(authorIdProvider.getBookId(), authorName, date);

        return authorRepository.save(author);
    }

    public Author getAuthorById(UUID id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name).orElse(null);
    }

}
