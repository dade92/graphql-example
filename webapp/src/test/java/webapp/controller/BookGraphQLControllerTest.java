package webapp.controller;

import data.Author;
import data.Book;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import repository.AuthorRepository;
import service.BookService;
import webapp.resolver.BookFieldResolver;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@GraphQlTest({BookGraphQLController.class, BookFieldResolver.class})
class BookGraphQLControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void testGetBookById_withResolvedAuthor() {
        UUID bookId = UUID.randomUUID();
        UUID authorId = UUID.randomUUID();

        Book book = new Book(bookId, "The Odyssey", "Epic poem", authorId);
        Author author = new Author(authorId, "Homer", "", LocalDate.parse("1970-01-01"));

        Mockito.when(bookService.findBookById(bookId)).thenReturn(book);
        Mockito.when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

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
        """, bookId);

        graphQlTester.document(query)
            .execute()
            .path("getBookById.title").entity(String.class).isEqualTo("The Odyssey")
            .path("getBookById.description").entity(String.class).isEqualTo("Epic poem")
            .path("getBookById.author.name").entity(String.class).isEqualTo("Homer")
            .path("getBookById.author.dateOfBirth").entity(String.class).isEqualTo("1970-01-01");
    }
}
