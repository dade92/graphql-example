package data;

import java.time.LocalDate;
import java.util.UUID;

public record Author(UUID id, String name, String surname, LocalDate dateOfBirth) {
}
