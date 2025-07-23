package com.raxrot.back.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteRequest {
    @NotBlank(message = "Note content cannot be blank")
    private String content;
}
