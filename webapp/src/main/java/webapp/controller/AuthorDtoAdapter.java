package webapp.controller;

import data.Author;

import java.time.format.DateTimeFormatter;

public class AuthorDtoAdapter {

    public AuthorDto adapt(Author author) {
        return new AuthorDto(
            author.id(),
            author.name(),
            author.dateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }

}
