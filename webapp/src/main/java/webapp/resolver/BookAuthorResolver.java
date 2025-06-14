package webapp.resolver;

import model.Author;
import model.Book;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import repository.AuthorRepository;
import repository.BookRepository;
import service.AuthorNotFoundException;
import webapp.adapter.AuthorResponse;
import webapp.adapter.AuthorResponseAdapter;

import java.util.UUID;

@Controller
public class BookAuthorResolver {

    private static final String FIELD_NAME = "author";

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorResponseAdapter authorResponseAdapter;

    public BookAuthorResolver(
        AuthorRepository authorRepository,
        BookRepository bookRepository,
        AuthorResponseAdapter authorResponseAdapter
    ) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorResponseAdapter = authorResponseAdapter;
    }

    @SchemaMapping(typeName = "Book", field = FIELD_NAME)
    public AuthorResponse author(Book book) {
        UUID authorId = bookRepository.getAuthor(book);
        Author author = authorRepository
            .findById(authorId)
            .orElseThrow(() -> new AuthorNotFoundException("Author not found: " + authorId));
        return authorResponseAdapter.adapt(author);
    }
}


