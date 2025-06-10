package webapp.resolver;

import model.Author;
import model.Book;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import repository.AuthorRepository;
import service.AuthorNotFoundException;

@Controller
public class BookAuthorFieldResolver {

    private final AuthorRepository authorRepository;

    public BookAuthorFieldResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Author author(Book book) {
        return authorRepository
            .findById(book.authorId())
            .orElseThrow(() -> new AuthorNotFoundException("Author not found: " + book.authorId()));
    }
}


