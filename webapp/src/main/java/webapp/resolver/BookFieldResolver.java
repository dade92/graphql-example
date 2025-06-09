package webapp.resolver;

import data.Author;
import data.Book;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import repository.AuthorRepository;

@Controller
public class BookFieldResolver {

    private final AuthorRepository authorRepository;

    public BookFieldResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Author author(Book book) {
        return authorRepository.findById(book.authorId())
            .orElseThrow(() -> new RuntimeException("Author not found: " + book.authorId()));
    }
}


