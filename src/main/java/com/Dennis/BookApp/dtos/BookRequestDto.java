package com.Dennis.BookApp.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Builder


public record BookRequestDto(
        Integer id,
        @NotNull
        @NotEmpty
        String title,
        @NotNull
        @NotEmpty
        String authorName,
        @NotNull
        @NotEmpty
        String isbn,
        @NotNull
        @NotEmpty
        String synopsis,
        boolean shareable

) {


}
