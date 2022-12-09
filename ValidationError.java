package com.ogreenwood.discord_music;

public class ValidationError extends Exception {

    public ValidationError(String errorMessage) {
        super(errorMessage);
    }
}
