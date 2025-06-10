package webapp.controller;

import data.Author;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import service.AuthorService;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;

@GraphQlTest(AuthorGraphQLController.class)
public class AuthorGraphQLControllerTest {

    public static final UUID ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private AuthorService authorService;

    @Test
    void createAuthor() {
        Author author = new Author(ID, "John", "Doe", LocalDate.parse("1970-01-01"));

        Mockito.when(authorService.createAuthor("John", "Doe", "1970-01-01"))
            .thenReturn(author);

        String mutation = """
                mutation {
                  createAuthor(name: "John", surname: "Doe", dateOfBirth: "1970-01-01") {
                    id
                    name
                    surname
                    dateOfBirth
                  }
                }
            """;

        graphQlTester.document(mutation)
            .execute()
            .path("createAuthor.name").entity(String.class).isEqualTo("John")
            .path("createAuthor.id").entity(String.class).isEqualTo("123e4567-e89b-12d3-a456-426614174000")
            .path("createAuthor.surname").entity(String.class).isEqualTo("Doe")
            .path("createAuthor.dateOfBirth").entity(String.class).isEqualTo("1970-01-01");
    }

    @Test
    void getAuthorById() {
        Author author = new Author(ID, "Jane", "Smith", LocalDate.parse("1985-05-20"));

        Mockito.when(authorService.getAuthorById(ID)).thenReturn(author);

        String query = String.format("""
                query {
                  getAuthorById(id: "%s") {
                    id
                    name
                    surname
                    dateOfBirth
                  }
                }
            """, ID);

        graphQlTester.document(query)
            .execute()
            .path("getAuthorById.id").entity(String.class).isEqualTo("123e4567-e89b-12d3-a456-426614174000")
            .path("getAuthorById.name").entity(String.class).isEqualTo("Jane")
            .path("getAuthorById.surname").entity(String.class).isEqualTo("Smith");
    }

    @Test
    void getAuthorByName() {
        Author author = new Author(ID, "Emily", "Bronte", LocalDate.parse("1818-07-30"));

        Mockito.when(authorService.getAuthorByName("Emily")).thenReturn(author);

        String query = """
                query {
                  getAuthorByName(name: "Emily") {
                    id
                    name
                    surname
                    dateOfBirth
                  }
                }
            """;

        graphQlTester.document(query)
            .execute()
            .path("getAuthorByName.id").entity(String.class).isEqualTo("123e4567-e89b-12d3-a456-426614174000")
            .path("getAuthorByName.surname").entity(String.class).isEqualTo("Bronte");
    }
}
