package webapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.AuthorRepository;
import service.AuthorService;

@Configuration
public class ServiceConfiguration {

    @Bean
    public AuthorService authorService(AuthorRepository authorRepository) {
        return new AuthorService(
            authorRepository
        );
    }
}
