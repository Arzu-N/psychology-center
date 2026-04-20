package org.example.psychology_center.exception;

public class NotFoundPsychologist extends RuntimeException {
    public NotFoundPsychologist(String message) {
        super(message);
    }
}
