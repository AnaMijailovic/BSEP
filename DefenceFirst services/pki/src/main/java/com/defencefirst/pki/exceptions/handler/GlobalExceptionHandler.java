package com.defencefirst.pki.exceptions.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.defencefirst.pki.exceptions.BadRequestException;
import com.defencefirst.pki.exceptions.ResourceNotFoundException;
import com.defencefirst.pki.exceptions.SavingException;


	
/*https://www.mkyong.com/spring-boot/spring-rest-error-handling-example/
https://dzone.com/articles/spring-rest-service-exception-handling-1
https://www.baeldung.com/exception-handling-for-rest-with-spring
*/
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> resourceNotFoundExceptionHandler(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);

    }
    
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomErrorResponse> badRequestExceptionHandler(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);

    }
    
    @ExceptionHandler(SavingException.class)
    public ResponseEntity<CustomErrorResponse> savingExceptionHandler(Exception ex, WebRequest request) {
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);

    }

}