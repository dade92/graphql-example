package webapp.controller;

import data.Author;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import service.AuthorService;

import java.util.UUID;

@Controller
public class AuthorGraphQLController {

    private final AuthorService authorService;

    public AuthorGraphQLController(
        AuthorService authorService
    ) {
        this.authorService = authorService;
    }

    @MutationMapping
    public Author createAuthor(
        @Argument String name,
        @Argument String surname,
        @Argument String dateOfBirth
    ) {
        return authorService.createAuthor(name, dateOfBirth);
    }

    @QueryMapping
    public Author getAuthorById(@Argument UUID id) {
        return authorService.getAuthorById(id);
    }

    @QueryMapping
    public Author getAuthorByName(@Argument String name) {
        return authorService.getAuthorByName(name);
    }
}