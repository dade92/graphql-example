package data;

import java.util.UUID;

public class Book {
    private final UUID id;
    private final String title;
    private final String description;
    private final UUID authorId;

    public Book(UUID id, String title, String description, UUID authorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.authorId = authorId;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getAuthorId() {
        return authorId;
    }
}
