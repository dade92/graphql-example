package webapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.AssetsRepository;
import repository.AuthorRepository;
import repository.BookRepository;
import repository.InMemoryAssetsRepository;
import repository.InMemoryAuthorRepository;
import repository.InMemoryBookRepository;

@Configuration
public class RepositoryConfiguration {
    @Bean
    public BookRepository bookRepository() {
        return new InMemoryBookRepository();
    }

    @Bean
    public AuthorRepository authorRepository() {
        return new InMemoryAuthorRepository();
    }

    @Bean
    public AssetsRepository assetRepository() {
        return new InMemoryAssetsRepository();
    }

}
