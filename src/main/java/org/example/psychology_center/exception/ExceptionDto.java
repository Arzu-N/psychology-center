package org.example.psychology_center.exception;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ExceptionDto {
    String message;
    LocalDateTime time;
    String path;
    int status;
    public ExceptionDto(String message, LocalDateTime time, String path, int status){
        this.message=message;
        this.time=time;
        this.path=path;
        this.status=status;
    }
}
