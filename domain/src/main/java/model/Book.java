package model;

import java.util.UUID;

public record Book(UUID id, String title, String description, UUID authorId) {
}
