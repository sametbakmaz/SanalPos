package com.sametbakmaz.SanalPosFups.exceptionHandler;

import com.sametbakmaz.SanalPosFups.common.QueryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<QueryResponse<String>> handleInvalidParameterException(InvalidParameterException ex) {
        QueryResponse<String> queryResponse = new QueryResponse<>();
        queryResponse.setStatus("fail");
        queryResponse.setMessage(ex.getMessage());
        queryResponse.setExceptionCode(ErrorCodeEnum.INVALID.getCode());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(queryResponse);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<QueryResponse<String>> handleNoSuchElementException(NoSuchElementException ex) {
        QueryResponse<String> queryResponse = new QueryResponse<>();
        queryResponse.setStatus("fail");
        queryResponse.setMessage(ex.getMessage());
        queryResponse.setExceptionCode(ErrorCodeEnum.NOT_FOUND.getCode());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(queryResponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<QueryResponse<String>> handleIllegalStateException(IllegalStateException ex) {
        QueryResponse<String> queryResponse = new QueryResponse<>();
        queryResponse.setStatus("fail");
        queryResponse.setMessage(ex.getMessage());
        queryResponse.setExceptionCode(ErrorCodeEnum.FAIL.getCode());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(queryResponse);
    }
}