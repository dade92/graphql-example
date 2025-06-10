package webapp.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import service.AuthorService;
import webapp.adapter.AuthorResponse;
import webapp.adapter.AuthorResponseAdapter;

import java.util.UUID;

@Controller
public class AuthorGraphQLController {

    private final AuthorService authorService;
    private final AuthorResponseAdapter authorResponseAdapter;

    public AuthorGraphQLController(
        AuthorService authorService,
        AuthorResponseAdapter authorResponseAdapter
    ) {
        this.authorService = authorService;
        this.authorResponseAdapter = authorResponseAdapter;
    }

    @MutationMapping
    public AuthorResponse createAuthor(
        @Argument String name,
        @Argument String dateOfBirth
    ) {
        return authorResponseAdapter.adapt(authorService.createAuthor(name, dateOfBirth));
    }

    @QueryMapping
    public AuthorResponse getAuthorById(@Argument UUID id) {
        return authorResponseAdapter.adapt(authorService.getAuthorById(id));
    }

    @QueryMapping
    public AuthorResponse getAuthorByName(@Argument String name) {
        return authorResponseAdapter.adapt(authorService.getAuthorByName(name));
    }
}
