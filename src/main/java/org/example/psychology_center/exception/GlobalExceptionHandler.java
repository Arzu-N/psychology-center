package org.example.psychology_center.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }


    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ExceptionDto> handleBaseException(
            BaseException ex,
            HttpServletRequest request
    ) {

        HttpStatus status = mapExceptionToStatus(ex);

        ExceptionDto dto = new ExceptionDto(
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI(),
                status.value()
        );

        return ResponseEntity
                .status(status)
                .body(dto);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGeneralException(
            Exception ex,
            HttpServletRequest request
    ) {

        ExceptionDto dto = new ExceptionDto(
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(dto);
    }


    private HttpStatus mapExceptionToStatus(BaseException ex) {

        if (ex instanceof NotFoundException) {
            return HttpStatus.NOT_FOUND;
        }

        if (ex instanceof ValidationException) {
            return HttpStatus.BAD_REQUEST;
        }

        if (ex instanceof UnauthorizedException) {
            return HttpStatus.UNAUTHORIZED;
        }

        if (ex instanceof ConflictException) {
            return HttpStatus.CONFLICT;
        }

        return HttpStatus.BAD_REQUEST;
    }
}