package com.example.deviceregistryapi.exception;

import java.time.LocalDateTime;

/**
 * A simple structure for returning error details to the client.
 */
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {}
