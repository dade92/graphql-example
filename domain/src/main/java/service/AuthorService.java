package service;

import data.Author;
import repository.AuthorRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(String name, String surname, String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dateOfBirth, formatter);
        Author author = new Author(UUID.randomUUID(), name, surname, date);

        return authorRepository.save(author);
    }

    public Author getAuthorById(UUID id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name).orElse(null);
    }

}
