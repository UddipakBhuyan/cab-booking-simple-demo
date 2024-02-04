package uddipak.cabbooking.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@RestControllerAdvice
public class AllExceptionHandler {
    @ExceptionHandler(AppUserNotFound.class)
    public ResponseEntity<ErrorDetails> appUserExceptionHandler(RuntimeException e, WebRequest wr){
        ErrorDetails error = new ErrorDetails(e.getMessage(), wr.getDescription(false), LocalDate.now());

        return ResponseEntity.badRequest().body(error);
    }
    @ExceptionHandler(DriverNotFound.class)
    public ResponseEntity<ErrorDetails> driverExceptionHandler(RuntimeException e, WebRequest wr){
        ErrorDetails error = new ErrorDetails(e.getMessage(), wr.getDescription(false), LocalDate.now());

        return ResponseEntity.badRequest().body(error);
    }
}
