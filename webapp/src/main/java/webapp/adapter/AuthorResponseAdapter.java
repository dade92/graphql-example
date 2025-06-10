package webapp.adapter;

import data.Author;

import java.time.format.DateTimeFormatter;

public class AuthorResponseAdapter {

    public AuthorResponse adapt(Author author) {
        return new AuthorResponse(
            author.id(),
            author.name(),
            author.dateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        );
    }

}
