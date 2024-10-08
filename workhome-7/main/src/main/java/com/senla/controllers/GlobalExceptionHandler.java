package com.senla.controllers;

import com.senla.exceptions.DaoCheckedException;
import com.senla.exceptions.DaoException;
import com.senla.models.user.User;
import com.senla.util.NotFoundByIdException;
import com.senla.util.dao.AbstractLongDao;
import com.senla.util.error.MapErrorResponse;
import com.senla.util.error.MessageErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //    ПОка не решил должно быть checked or uncheked exception class
    @ExceptionHandler(DaoException.class)
    public ResponseEntity<MessageErrorResponse> handleDaoException(DaoException e) {
//        System.out.println("UNCHECKED EXCEPTION");
//        logger.info("DaoException.class: {}", e.getMessage(), e);
        return new ResponseEntity<>(
                new MessageErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), System.currentTimeMillis()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NotFoundByIdException.class)
    public ResponseEntity<MessageErrorResponse> handleNotFoundByIdException(NotFoundByIdException e) {
//        logger.info("NotFoundByIdException.class: {}", e.getMessage(), e);
        return new ResponseEntity<>(
                new MessageErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis()),
                    HttpStatus.NOT_FOUND);
    }
//    @ExceptionHandler(DaoCheckedException.class)
//    public ResponseEntity<?> handleDaoCheckedException(DaoCheckedException e) {
//            System.out.println("CHECKED EXCEPTION");
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    на валидацию requestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MapErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        logger.info("MethodArgumentNotValidException.class: {}", ex.getMessage(), ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new MapErrorResponse(HttpStatus.BAD_REQUEST.value(), errors, System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

//    на валидацию pathVariable
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<MessageErrorResponse> handleValidationExceptions(MethodArgumentTypeMismatchException ex) {
//        logger.info("MethodArgumentTypeMismatchException.class: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new MessageErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }

//    кидается если был runtime excep
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
//        logger.info("ConstraintViolationException.class: {}", ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(ex.getConstraintViolations());
    }

//    ConstraintViolationException
//   !!!!!!!!!!!!!!!!!!1
//   !!!!!!!!!!!!!!!!!!
//    !!!!!!!!!!!!!!!!
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handle1(DataIntegrityViolationException e) {
//        logger.info("DataIntegirit SHIIIT .class: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(jakarta.servlet.ServletException.class)
    public ResponseEntity<?> handle2(ServletException e) {
//        logger.info("Servlet SHIIT .class: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
