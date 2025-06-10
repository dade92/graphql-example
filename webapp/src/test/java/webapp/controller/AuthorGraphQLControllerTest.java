package webapp.controller;

import model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import service.AuthorService;
import utils.FixtureLoader;
import webapp.adapter.AuthorResponse;
import webapp.adapter.AuthorResponseAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.mockito.Mockito.when;

@GraphQlTest(AuthorGraphQLController.class)
public class AuthorGraphQLControllerTest {

    private static final UUID ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private static final String NAME = "John";
    public static final String DATE = "01/01/1970";
    private static final Author AUTHOR = new Author(
        ID,
        NAME,
        LocalDate.parse(DATE, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    );
    private static final AuthorResponse AUTHOR_DTO = new AuthorResponse(
        ID,
        NAME,
        DATE
    );

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private AuthorResponseAdapter authorResponseAdapter;

    @BeforeEach
    void setUp() {
        when(authorResponseAdapter.adapt(AUTHOR)).thenReturn(AUTHOR_DTO);
    }

    @Test
    void createAuthor() {
        when(authorService.createAuthor(NAME, DATE)).thenReturn(AUTHOR);

        String mutation = """
                mutation {
                  createAuthor(name: "John", dateOfBirth: "01/01/1970") {
                    id
                    name
                    dateOfBirth
                  }
                }
            """;

        graphQlTester.document(mutation)
            .execute()
            .path("createAuthor").matchesJson(
                FixtureLoader.readFile("/responses/author.json")
            );
    }

    @Test
    void getAuthorById() {
        when(authorService.getAuthorById(ID)).thenReturn(AUTHOR);

        String query = String.format("""
                query {
                  getAuthorById(id: "%s") {
                    id
                    name
                    dateOfBirth
                  }
                }
            """, ID);

        graphQlTester.document(query)
            .execute()
            .path("getAuthorById").matchesJson(
                FixtureLoader.readFile("/responses/author.json")
            );
    }

    @Test
    void getAuthorByName() {
        when(authorService.getAuthorByName(NAME)).thenReturn(AUTHOR);

        String query = String.format("""
                query {
                  getAuthorByName(name: "%s") {
                    id
                    name
                    dateOfBirth
                  }
                }
            """, NAME);

        graphQlTester.document(query)
            .execute()
            .path("getAuthorByName").matchesJson(
                FixtureLoader.readFile("/responses/author.json")
            );
    }
}
