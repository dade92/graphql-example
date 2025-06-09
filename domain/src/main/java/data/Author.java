package data;

import java.util.UUID;

public record Author(UUID id, String name, String surname, String dateOfBirth) {
}
