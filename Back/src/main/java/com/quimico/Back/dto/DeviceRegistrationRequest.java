package com.quimico.Back.dto;

import jakarta.validation.constraints.NotBlank;

public record DeviceRegistrationRequest(
        @NotBlank String googleUserId,
        @NotBlank String deviceToken
) {
}
