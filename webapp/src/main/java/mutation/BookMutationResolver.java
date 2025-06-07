package mutation;

import data.Author;
import data.Book;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import repository.AuthorRepository;
import repository.BookRepository;

import java.util.UUID;

@Component
public class BookMutationResolver implements GraphQLMutationResolver {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;

    public BookMutationResolver(BookRepository bookRepo, AuthorRepository authorRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    public Book createBook(String title, String description, String authorName) {
        Author author = authorRepo.findByName(authorName).orElseThrow();
        Book book = new Book(UUID.randomUUID(), title, description, author);
        return bookRepo.save(book);
    }

    //TODO move to another mutation resolver
    public Author createAuthor(String name, String surname, String dateOfBirth) {
        Author author = new Author(UUID.randomUUID(), name, surname, dateOfBirth);
        return authorRepo.save(author);
    }
}

