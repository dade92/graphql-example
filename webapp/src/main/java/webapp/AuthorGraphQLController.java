package webapp;

import data.Author;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import repository.AuthorRepository;

import java.util.UUID;

@Controller
public class AuthorGraphQLController {

    private final AuthorRepository authorRepository;

    public AuthorGraphQLController(
        AuthorRepository authorRepository
    ) {
        this.authorRepository = authorRepository;
    }

    @MutationMapping
    public Author createAuthor(
        @Argument String name,
        @Argument String surname,
        @Argument String dateOfBirth
    ) {
        Author author = new Author(UUID.randomUUID(), name, surname, dateOfBirth);
        return authorRepository.save(author);
    }

    @QueryMapping
    public Author getAuthorById(@Argument UUID id) {
        return authorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Author not found with id: " + id));
    }

    @QueryMapping
    public Author getAuthorByName(@Argument String name) {
        return authorRepository.findByName(name)
            .orElseThrow(() -> new IllegalArgumentException("Author not found with name: " + name));
    }
}

