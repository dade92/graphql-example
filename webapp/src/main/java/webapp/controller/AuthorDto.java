package webapp.controller;

import java.util.UUID;

public record AuthorDto(UUID id, String name, String dateOfBirth) {
}
