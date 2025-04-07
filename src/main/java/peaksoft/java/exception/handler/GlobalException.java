package peaksoft.java.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import peaksoft.java.dto.response.ExceptionResponse;
import peaksoft.java.exception.AlreadyExistException;
import peaksoft.java.exception.BadRequestException;
import peaksoft.java.exception.NotFoundException;

import java.rmi.AlreadyBoundException;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    public ExceptionResponse notFound(NotFoundException notfoundException) {
        return ExceptionResponse.
                builder()
                .status(HttpStatus.NOT_FOUND)
                .exceptionsClassName(NotFoundException.class.getName())
                .message(notfoundException.getMessage())
                .build();
    }
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.IM_USED)  //
    public ExceptionResponse exists(AlreadyExistException e) {
        return ExceptionResponse.
                builder()
                .status(HttpStatus.IM_USED)
                .exceptionsClassName(AlreadyExistException.class.getName())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    public ExceptionResponse badRequest(BadRequestException e) {
        return ExceptionResponse.
                builder()
                .status(HttpStatus.BAD_REQUEST)
                .exceptionsClassName(BadRequestException.class.getName())
                .message(e.getMessage())
                .build();

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse methodArgNotValidType(MethodArgumentTypeMismatchException methodArgumentNotValidTypeException) {
        return ExceptionResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .exceptionsClassName(methodArgumentNotValidTypeException.getClass().getSimpleName()).
                message(methodArgumentNotValidTypeException.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}

