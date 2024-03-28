package com.backend.clinicaOdontologica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> manejarResourceNotFound(ResourceNotFoundException resourceNotFoundException){
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Recurso no encontrado: " + resourceNotFoundException.getMessage());
        return mensaje;
    }

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarBadRequest(BadRequestException badRequestException) {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("mensaje", "Peticion incorrecta: " + badRequestException.getMessage());
        return mensaje;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> manejarValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> mensaje = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(e -> {
            String nombreCampo = ((FieldError) e).getField();
            String mensajeError = e.getDefaultMessage();
            mensaje.put(nombreCampo, mensajeError);
        });
        return mensaje;
    }
}
