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
        System.out.println("BookFieldResolver constructor");
        this.authorRepository = authorRepository;
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Author author(Book book) {
        System.out.println("Resolving author for book: " + book.id());
        return authorRepository.findById(book.authorId())
            .orElseThrow(() -> new RuntimeException("Author not found: " + book.authorId()));
    }
}


