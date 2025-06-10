package webapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import provider.AuthorIdProvider;
import provider.BookIdProvider;
import repository.AuthorRepository;
import repository.BookRepository;
import service.AuthorService;
import service.BookService;
import utils.AuthorNameNormalizer;
import webapp.adapter.AuthorResponseAdapter;

@Configuration
public class ServiceConfiguration {

    @Bean
    public AuthorService authorService(AuthorRepository authorRepository) {
        return new AuthorService(
            authorRepository,
            new AuthorIdProvider(),
            new AuthorNameNormalizer()
        );
    }

    @Bean
    public BookService bookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        return new BookService(
            bookRepository,
            authorRepository,
            new BookIdProvider()
        );
    }

    @Bean
    public AuthorResponseAdapter authorResponseAdapter() {
        return new AuthorResponseAdapter();
    }
}
