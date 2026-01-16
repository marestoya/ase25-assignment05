package de.unibayreuth.se.taskboard.api.dtos;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

import java.util.UUID;
//TODO: Add DTO for users.
@Data
public class UserDto {

    @Nullable
    private final UUID id;

    @NotNull
    @NotBlank
    @Size(max = 255, message = "Name is limited to 255 characters!")
    private final String name;

    @Nullable
    private final LocalDateTime createdAt; 
    @Nullable
    private final LocalDateTime updatedAt; 
}
