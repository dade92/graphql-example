package webapp;

import data.Author;
import data.Book;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import repository.AuthorRepository;
import repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class BookGraphQLController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookGraphQLController(
        BookRepository bookRepository,
        AuthorRepository authorRepository
    ) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @MutationMapping
    public Book createBook(
        @Argument String title,
        @Argument String description,
        @Argument String authorName
    ) {
        Optional<Author> author = authorRepository.findByName(authorName);
        if (author.isEmpty()) {
            throw new IllegalArgumentException("Author not found: " + authorName);
        }
        Book book = new Book(UUID.randomUUID(), title, description, author.get());
        return bookRepository.save(book);
    }

    @QueryMapping
    public Book getBookById(@Argument UUID id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
    }

    @QueryMapping
    public Book getBookByTitle(@Argument String title) {
        return bookRepository.findByTitle(title)
            .orElseThrow(() -> new IllegalArgumentException("Book not found with title: " + title));
    }

    @QueryMapping
    public List<Book> getBooksByAuthor(@Argument UUID authorId) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) {
            throw new IllegalArgumentException("Author not found: " + authorId);
        }
        return bookRepository.findByAuthor(author.get());
    }


}

