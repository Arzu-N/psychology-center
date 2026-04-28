package org.example.psychology_center.exception;

import java.time.LocalDateTime;

public class ExceptionDto {
    String message;
    LocalDateTime time;
    String path;
    int status;
    public ExceptionDto(String message,LocalDateTime time,String path,int status){
        this.message=message;
        this.time=time;
        this.path=path;
        this.status=status;
    }
}
