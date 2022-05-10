package com.springboot.bowling.exception;

import com.springboot.bowling.payload.response.ErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Catches thrown exceptions and creates a response entity with
 * details about the exceptions that we choose to show the user
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Sends an error dto on behalf of my custom {@link ResourceNotFoundException}
     * @param exception
     * @param request
     * @return Detailed error message informing the user that their resource was not found + 404 status code
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        ErrorDto error = new ErrorDto(HttpStatus.NOT_FOUND.getReasonPhrase(),
                                        new Date(),
                                        exception.getMessage(),
                                        request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Sends an error dto on behalf of my custom {@link BadRequestException}
     * @param exception
     * @param request
     * @return Error message with HttpStatus.BAD_REQUEST status informing the client of their bad request
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDto> handleBlogApiException(BadRequestException exception, WebRequest request) {
        ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                        new Date(),
                                        exception.getMessage(),
                                        request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Sends a list of errors on behalf of MethodArgumentNotValidException
     * @param exception
     * @param headers
     * @param status
     * @param request
     * @return error details + 400 status code
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request){
        Map<String,String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName,message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * For simplicity, when anything else goes wrong just return an internal server error
     * @param exception
     * @param request
     * @return error details + 500 status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGlobalException(Exception exception, WebRequest request) {
        ErrorDto error = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                        new Date(),
                                        exception.getMessage(),
                                        request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
