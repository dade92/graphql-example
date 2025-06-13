package webapp.controller;

import model.Asset;
import model.Author;
import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import repository.AssetsRepository;
import repository.AuthorRepository;
import repository.BookRepository;
import service.BookService;
import webapp.adapter.AuthorResponse;
import webapp.adapter.AuthorResponseAdapter;
import webapp.resolver.BookAssetsResolver;
import webapp.resolver.BookAuthorResolver;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static utils.FixtureLoader.readFile;

@GraphQlTest({BookGraphQLController.class, BookAuthorResolver.class, BookAssetsResolver.class})
class BookGraphQLControllerTest {
    private static final UUID BOOK_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private static final UUID AUTHOR_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
    private static final String AUTHOR_NAME = "Homer";
    private static final String TITLE = "The Odyssey";
    private static final String DESCRIPTION = "Epic poem";
    private static final Author AUTHOR = new Author(AUTHOR_ID, AUTHOR_NAME, LocalDate.parse("1970-01-01"));
    private static final Book BOOK = new Book(BOOK_ID, TITLE, DESCRIPTION);
    private static final Asset ASSET = new Asset("IMAGE", "url");

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private AuthorResponseAdapter authorResponseAdapter;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AssetsRepository assetsRepository;

    @BeforeEach
    void setUp() {
        when(authorResponseAdapter.adapt(AUTHOR)).thenReturn(new AuthorResponse(
            AUTHOR_ID, AUTHOR_NAME, "01/01/1970"
        ));
        when(bookRepository.getAuthor(BOOK)).thenReturn(AUTHOR_ID);
        when(assetsRepository.retrieveAssets(BOOK)).thenReturn(List.of(ASSET));
    }

    @Test
    void createBook() {
        when(bookService.createBook(TITLE, DESCRIPTION, AUTHOR_NAME)).thenReturn(BOOK);
        when(authorRepository.findById(AUTHOR_ID)).thenReturn(Optional.of(AUTHOR));

        String query = String.format("""
                mutation {
                  createBook(title: "%s", description: "%s", authorName: "%s") {
                    id
                    title
                    description
                    author {
                      id
                      name
                      dateOfBirth
                    }
                    assets {
                        type
                        url
                    }
                  }
                }
            """, TITLE, DESCRIPTION, AUTHOR_NAME);

        graphQlTester.document(query)
            .execute()
            .path("createBook").matchesJsonStrictly(
                readFile("/responses/book.json")
            );
    }

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
                      dateOfBirth
                    }
                    assets {
                        type
                        url
                    }
                  }
                }
            """, BOOK_ID);

        graphQlTester.document(query)
            .execute()
            .path("getBookById").matchesJsonStrictly(
                readFile("/responses/book.json")
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
                      dateOfBirth
                    }
                    assets {
                        type
                        url
                    }
                  }
                }
            """, TITLE);

        graphQlTester.document(query)
            .execute()
            .path("getBookByTitle").matchesJsonStrictly(
                readFile("/responses/book.json")
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
                      dateOfBirth
                    }
                    assets {
                        type
                        url
                    }
                  }
                }
            """, AUTHOR_ID);

        graphQlTester.document(query)
            .execute()
            .path("getBooksByAuthor").matchesJsonStrictly(
                readFile("/responses/booksByAuthor.json")
            );
    }
}
