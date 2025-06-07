package webapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.InMemoryAuthorRepository;
import repository.InMemoryBookRepository;
import webapp.resolver.BookFieldResolver;

@Configuration
public class GraphQLConfig {
    @Bean
    public BookRepository bookRepository() {
        return new InMemoryBookRepository();
    }

    @Bean
    public AuthorRepository authorRepository() {
        return new InMemoryAuthorRepository();
    }


    @Bean
    public BookFieldResolver bookFieldResolver(AuthorRepository authorRepository) {
        return new BookFieldResolver(authorRepository);
    }
}
