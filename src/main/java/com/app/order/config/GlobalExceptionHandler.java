package com.app.order.config;

import com.app.order.dto.ErrorMessageDTO;
import com.app.order.util.OrderCancelException;
import com.app.order.util.OrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ErrorMessageDTO> handleIllegalArgumentException(IllegalArgumentException ex){
        List<String> errorMessages = new ArrayList<>(Collections.singleton(ex.getMessage()));
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(errorMessages);
        log.error(errorMessageDTO.getErrorMessages().toString());

        return new ResponseEntity<>(errorMessageDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({OrderException.class,
                        OrderCancelException.class})
    public ResponseEntity<ErrorMessageDTO> handleOrderException(Exception ex) {
        List<String> errorMessages = new ArrayList<>(Collections.singleton(ex.getMessage()));
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(errorMessages);
        log.error(errorMessageDTO.getErrorMessages().toString());

        return new ResponseEntity<>(errorMessageDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        final List<FieldError> errorMessages = ex.getBindingResult().getFieldErrors();
        final List<String> errorMessage = errorMessages.stream()
                .map(error -> error.getDefaultMessage())
                .toList();
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO(errorMessage);
        log.error(errorMessageDTO.getErrorMessages().toString());

        return new ResponseEntity<>(errorMessageDTO, HttpStatus.BAD_REQUEST);
    }
}
