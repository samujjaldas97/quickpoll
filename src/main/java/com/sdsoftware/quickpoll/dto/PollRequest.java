package com.sdsoftware.quickpoll.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PollRequest {

    @NotBlank(message = "Question cannot be blank")
    private String question;

    @NotEmpty(message = "Options cannot be empty")
    @Size(min = 2, message = "There must be at least two options")
    private List<String> options;
}
