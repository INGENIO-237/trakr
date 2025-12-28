package app.vercel.ingenio_theta.trakr.shared.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ExceptionResponse;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().stream().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(ExceptionResponse.builder()
                .errors(errors)
                .message("Validation error")
                .status(HttpStatus.BAD_REQUEST)
                .build());
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> handleCommonExceptions(ApiException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ExceptionResponse
                        .builder()
                        .message(ex.getMessage())
                        .status(ex.getStatus())
                        .timestamp(ex.getTimestamp())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleInternalServerError(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse
                        .builder()
                        .message("Internal server error")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .timestamp(LocalDateTime.now())
                        .build());
    }
}
