package br.com.projetorest.api.resources.exceptions;


import br.com.projetorest.api.services.exceptions.DataIntegratyViolationException;
import br.com.projetorest.api.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandarError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request) {
StandarError error =
        new StandarError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(),request.getRequestURI());
return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandarError> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        StandarError error =
                new StandarError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
