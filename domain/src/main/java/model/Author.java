package model;

import java.time.LocalDate;
import java.util.UUID;

public record Author(UUID id, String name, LocalDate dateOfBirth) {
}
