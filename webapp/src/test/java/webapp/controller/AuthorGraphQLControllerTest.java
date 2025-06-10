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

@GraphQlTest(AuthorGraphQLController.class)
public class AuthorGraphQLControllerTest {

    private static final UUID ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private static final String NAME = "John";
    private static final String SURNAME = "Doe";
    private static final Author AUTHOR = new Author(ID, NAME, SURNAME, LocalDate.parse("1970-01-01"));

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private AuthorService authorService;

    @Test
    void createAuthor() {
        Mockito.when(authorService.createAuthor(NAME, SURNAME, "1970-01-01"))
            .thenReturn(AUTHOR);

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
            .path("createAuthor.name").entity(String.class).isEqualTo(NAME)
            .path("createAuthor.surname").entity(String.class).isEqualTo(SURNAME)
            .path("createAuthor.id").entity(String.class).isEqualTo("123e4567-e89b-12d3-a456-426614174000")
            .path("createAuthor.dateOfBirth").entity(String.class).isEqualTo("1970-01-01");
    }

    @Test
    void getAuthorById() {
        Mockito.when(authorService.getAuthorById(ID)).thenReturn(AUTHOR);

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
            .path("getAuthorById.name").entity(String.class).isEqualTo(NAME)
            .path("getAuthorById.surname").entity(String.class).isEqualTo(SURNAME);
    }

    @Test
    void getAuthorByName() {
        Mockito.when(authorService.getAuthorByName(NAME)).thenReturn(AUTHOR);

        String query = String.format("""
                query {
                  getAuthorByName(name: "%s") {
                    id
                    name
                    surname
                    dateOfBirth
                  }
                }
            """, NAME);

        graphQlTester.document(query)
            .execute()
            .path("getAuthorByName.id").entity(String.class).isEqualTo("123e4567-e89b-12d3-a456-426614174000")
            .path("getAuthorByName.name").entity(String.class).isEqualTo(NAME)
            .path("getAuthorByName.surname").entity(String.class).isEqualTo(SURNAME);
    }
}
