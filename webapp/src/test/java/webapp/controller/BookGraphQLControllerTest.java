package webapp.controller;

import data.Author;
import data.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import repository.AuthorRepository;
import service.BookService;
import webapp.resolver.BookFieldResolver;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;

@GraphQlTest({BookGraphQLController.class, BookFieldResolver.class})
class BookGraphQLControllerTest {

    public static final UUID BOOK_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    public static final UUID AUTHOR_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
    public static final String AUTHOR_NAME = "Homer";
    public static final Author AUTHOR = new Author(AUTHOR_ID, AUTHOR_NAME, "", LocalDate.parse("1970-01-01"));
    public static final String TITLE = "The Odyssey";
    public static final String DESCRIPTION = "Epic poem";
    public static final Book BOOK = new Book(BOOK_ID, TITLE, DESCRIPTION, AUTHOR_ID);


    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void getBookByID() {
        when(bookService.findBookById(BOOK_ID)).thenReturn(BOOK);
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(AUTHOR));

        String query = String.format("""
                query {
                  getBookById(id: "%s") {
                    id
                    title
                    description
                    author {
                      id
                      name
                      surname
                      dateOfBirth
                    }
                  }
                }
            """, BOOK_ID);

        graphQlTester.document(query)
            .execute()
            .path("getBookById").matchesJson(
                """
                    {
                         "id": "123e4567-e89b-12d3-a456-426614174000",
                         "title": "The Odyssey",
                         "description": "Epic poem",
                         "author": {
                           "id": "123e4567-e89b-12d3-a456-426614174001",
                           "name": "Homer",
                           "surname": "",
                           "dateOfBirth": "1970-01-01"
                         }
                    }
                    """
            );
    }

    @Test
    void getBookByName() {
        when(bookService.findBookByTitle(TITLE)).thenReturn(BOOK);
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(AUTHOR));

        String query = String.format("""
                query {
                  getBookByTitle(title: "%s") {
                    id
                    title
                    description
                    author {
                      id
                      name
                      surname
                      dateOfBirth
                    }
                  }
                }
            """, TITLE);

        graphQlTester.document(query)
            .execute()
            .path("getBookByTitle").matchesJson(
                """
                    {
                         "id": "123e4567-e89b-12d3-a456-426614174000",
                         "title": "The Odyssey",
                         "description": "Epic poem",
                         "author": {
                           "id": "123e4567-e89b-12d3-a456-426614174001",
                           "name": "Homer",
                           "surname": "",
                           "dateOfBirth": "1970-01-01"
                         }
                    }
                    """
            );
    }

    @Test
    void getBooksByAuthor() {
        when(bookService.findByAuthor(AUTHOR_ID)).thenReturn(List.of(BOOK, BOOK));
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(AUTHOR));

        String query = String.format("""
                query {
                  getBooksByAuthor(authorId: "%s") {
                    id
                    title
                    description
                    author {
                      id
                      name
                      surname
                      dateOfBirth
                    }
                  }
                }
            """, AUTHOR_ID);

        graphQlTester.document(query)
            .execute()
            .path("getBooksByAuthor").matchesJson(
                """
                    [
                        {
                             "id": "123e4567-e89b-12d3-a456-426614174000",
                             "title": "The Odyssey",
                             "description": "Epic poem",
                             "author": {
                               "id": "123e4567-e89b-12d3-a456-426614174001",
                               "name": "Homer",
                               "surname": "",
                               "dateOfBirth": "1970-01-01"
                              }
                         },
                         {
                             "id": "123e4567-e89b-12d3-a456-426614174000",
                             "title": "The Odyssey",
                             "description": "Epic poem",
                             "author": {
                               "id": "123e4567-e89b-12d3-a456-426614174001",
                               "name": "Homer",
                               "surname": "",
                               "dateOfBirth": "1970-01-01"
                             }
                         }
                    ]
                    """
            );
    }
}
