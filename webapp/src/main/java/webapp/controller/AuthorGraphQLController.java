package webapp.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import service.AuthorService;

import java.util.UUID;

@Controller
public class AuthorGraphQLController {

    private final AuthorService authorService;
    private final AuthorDtoAdapter authorDtoAdapter;

    public AuthorGraphQLController(
        AuthorService authorService,
        AuthorDtoAdapter authorDtoAdapter
    ) {
        this.authorService = authorService;
        this.authorDtoAdapter = authorDtoAdapter;
    }

    @MutationMapping
    public AuthorDto createAuthor(
        @Argument String name,
        @Argument String dateOfBirth
    ) {
        return authorDtoAdapter.adapt(authorService.createAuthor(name, dateOfBirth));
    }

    @QueryMapping
    public AuthorDto getAuthorById(@Argument UUID id) {
        return authorDtoAdapter.adapt(authorService.getAuthorById(id));
    }

    @QueryMapping
    public AuthorDto getAuthorByName(@Argument String name) {
        return authorDtoAdapter.adapt(authorService.getAuthorByName(name));
    }
}
