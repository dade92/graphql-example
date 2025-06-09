package provider;

import java.util.UUID;

public class BookIdProvider {

    public UUID getBookId() {
        return UUID.randomUUID();
    }

}
