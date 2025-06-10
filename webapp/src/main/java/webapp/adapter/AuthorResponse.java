package webapp.adapter;

import java.util.UUID;

public record AuthorResponse(UUID id, String name, String dateOfBirth) {
}
