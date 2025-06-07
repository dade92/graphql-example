package webapp.resolver;

import data.Author;
import data.Book;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import repository.AuthorRepository;

import java.util.UUID;

public class BookFieldResolver {

    private final AuthorRepository authorRepository;

    public BookFieldResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Author resolveAuthor(Book book) {
        UUID authorId = book.getAuthor().getId();

        return authorRepository.findById(authorId)
            .orElseThrow(() -> new RuntimeException("Author not found: " + authorId));
    }
}

