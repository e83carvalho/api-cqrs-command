package br.com.egc.command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.util.Optional.ofNullable;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<Object> handleException(Throwable throwable) {

        BusinessException ex = BusinessException.builder()
                .httpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(ofNullable(throwable.getMessage()).orElse(throwable.toString()))

                .build();
        return ResponseEntity.status(ex.getHttpStatusCode()).body(ex.getOnlyBody());

    }


}
