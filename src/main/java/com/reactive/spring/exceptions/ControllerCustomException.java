//package com.reactive.spring.exceptions;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
//
//@ControllerAdvice
//@Slf4j
//public class ControllerCustomException {
//
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<String> handleRuntimeException(RuntimeException error) {
//        log.error("Handle RuntimeException: {0} " + error);
//        return ResponseEntity
//                .status(INTERNAL_SERVER_ERROR)
//                .body(error.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception error) {
//        log.error("Handle Exception: {0} " + error);
//        return ResponseEntity
//                .status(INTERNAL_SERVER_ERROR)
//                .body(error.getMessage());
//    }
//}
