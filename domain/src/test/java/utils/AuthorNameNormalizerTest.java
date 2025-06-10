package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorNameNormalizerTest {

    private final AuthorNameNormalizer authorNameNormalizer = new AuthorNameNormalizer();

    private static final String EXPECTED_NAME = "John Doe";

    @Test
    void get() {
        assertEquals(EXPECTED_NAME, authorNameNormalizer.get(EXPECTED_NAME));
        assertEquals(EXPECTED_NAME, authorNameNormalizer.get("john Doe"));
        assertEquals(EXPECTED_NAME, authorNameNormalizer.get("John doe"));
    }
}