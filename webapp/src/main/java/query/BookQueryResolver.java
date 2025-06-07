package query;

import data.Author;
import data.Book;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;
import repository.AuthorRepository;
import repository.BookRepository;

import java.util.UUID;

@Component
public class BookQueryResolver implements GraphQLQueryResolver {
    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;

    public BookQueryResolver(BookRepository bookRepo, AuthorRepository authorRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    public Book getBookById(UUID id) {
        return bookRepo.findById(id).orElse(null);
    }

    public Book getBookByTitle(String title) {
        return bookRepo.findByTitle(title).orElse(null);
    }

    //TODO move to another query resolver
    public Author getAuthorById(UUID id) {
        return authorRepo.findById(id).orElse(null);
    }

    //TODO move to another query resolver
    public Author getAuthorByName(String name) {
        return authorRepo.findByName(name).orElse(null);
    }
}
