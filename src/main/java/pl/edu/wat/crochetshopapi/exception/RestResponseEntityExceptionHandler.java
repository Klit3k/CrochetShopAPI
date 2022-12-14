package pl.edu.wat.crochetshopapi.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            ProductNotFoundException.class,
            ClientNotFoundException.class,
            OrderNotFoundException.class,
            VariantNotFoundException.class,
            ImageNotFoundException.class,
            PromoCodeNotFoundException.class
    })
    protected ResponseEntity<?> xNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatusCode.valueOf(404), request);
    }

    @ExceptionHandler(value = {
            InvalidTypeOfFileException.class,
            EmptyCartException.class,
            ClientAlreadyExistsException.class,
            ProductAlreadyInVariantException.class,
            VariantAlreadyExistsException.class,
            ImageAlreadyExistsException.class,
            PromoCodeAlreadyExistsException.class})
    protected ResponseEntity<?> xInvalid(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatusCode.valueOf(400), request);
    }


}