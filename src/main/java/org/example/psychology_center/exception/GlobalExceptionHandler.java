package org.example.psychology_center.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>>handleValidation(MethodArgumentNotValidException ex){
        Map<String,String> map=new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error->map.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionDto>handle(RuntimeException ex, ServletWebRequest request){
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), LocalDateTime.now(),
                request.getRequest().getRequestURI(),400);
        return ResponseEntity.badRequest().body(exceptionDto);
    }
}
