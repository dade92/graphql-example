package webapp.resolver;

import model.Author;
import model.Book;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import repository.AuthorRepository;
import repository.BookRepository;
import service.AuthorNotFoundException;

import java.util.UUID;

@Controller
public class BookAuthorFieldResolver {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookAuthorFieldResolver(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @SchemaMapping(typeName = "Book", field = "author")
    public Author author(Book book) {
        UUID authorId = bookRepository.getAuthor(book);
        return authorRepository
            .findById(authorId)
            .orElseThrow(() -> new AuthorNotFoundException("Author not found: " + authorId));
    }
}


