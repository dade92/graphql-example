package provider;

import java.util.UUID;

public class AuthorIdProvider {

    public UUID getBookId() {
        return UUID.randomUUID();
    }

}
