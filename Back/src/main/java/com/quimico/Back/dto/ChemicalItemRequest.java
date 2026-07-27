package com.quimico.Back.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ChemicalItemRequest(
        @NotBlank String name,
        @NotBlank String sapNumber,
        @NotNull @FutureOrPresent LocalDate expirationDate,
        @NotNull @Min(1) Integer quantity,
        @NotBlank String location
) {
}
