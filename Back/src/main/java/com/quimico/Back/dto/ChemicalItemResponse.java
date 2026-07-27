package com.quimico.Back.dto;

import java.time.LocalDate;

public record ChemicalItemResponse(
        Long id,
        String name,
        String sapNumber,
        LocalDate expirationDate,
        Integer quantity,
        String location,
        boolean notifiedOneDayBefore,
        boolean notifiedOnExpiration
) {
}
