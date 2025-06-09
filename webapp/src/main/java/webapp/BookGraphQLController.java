package webapp;

import data.Book;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import service.BookService;

import java.util.List;
import java.util.UUID;

@Controller
public class BookGraphQLController {

    private final BookService bookService;

    public BookGraphQLController(
        BookService bookService
    ) {
        this.bookService = bookService;
    }

    @MutationMapping
    public Book createBook(
        @Argument String title,
        @Argument String description,
        @Argument String authorName
    ) {
        return bookService.createBook(title, description, authorName);
    }

    @QueryMapping
    public Book getBookById(@Argument UUID id) {
        return bookService.findBookById(id);
    }

    @QueryMapping
    public Book getBookByTitle(@Argument String title) {
        return bookService.findBookByTitle(title);
    }

    @QueryMapping
    public List<Book> getBooksByAuthor(@Argument UUID authorId) {
        return bookService.findByAuthor(authorId);
    }
}