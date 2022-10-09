package sod.eastonone.music.es.controlleradvice;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import sod.eastonone.music.es.exception.RecordNotFoundException;

@Slf4j
@RestControllerAdvice
public class ESRestControllerAdvice {
    @Getter
    @Setter
//    @AllArgsConstructor //Maven build works fine, for some reason Eclipse doesn't recognize the annotation and I had to add the all args constructor myself
    class Error{
        private String message;
        private int code;
        
        public Error(String message, int code) {
    		super();
    		this.message = message;
    		this.code = code;
    	}
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Error> handleRecordNotFoundException(RecordNotFoundException exception) {
        //log.info(exception.getMessage());
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Error> handleIOException(IOException exception) {
        //log.error(exception.getMessage());
        return ResponseEntity.internalServerError().body(new Error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception exception) {
        //log.error(exception.getMessage());
        return ResponseEntity.internalServerError().body(new Error(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}
