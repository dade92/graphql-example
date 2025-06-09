package webapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.AuthorRepository;
import repository.BookRepository;
import service.AuthorService;
import service.BookService;

@Configuration
public class ServiceConfiguration {

    @Bean
    public AuthorService authorService(AuthorRepository authorRepository) {
        return new AuthorService(
            authorRepository
        );
    }

    @Bean
    public BookService bookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        return new BookService(
            bookRepository,
            authorRepository
        );
    }
}
