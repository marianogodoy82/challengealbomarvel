package com.albo.challengealbomarvel.errorhandling;

import com.albo.challengealbomarvel.dto.GenericApiRsDto;
import com.albo.challengealbomarvel.exception.NotFoundHeroException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {HttpClientErrorException.class})
    protected ResponseEntity<Object> handleHttpClientErrorException(RuntimeException ex, WebRequest request) {
        log.debug("handleHttpClientErrorException");
        log.error(getMensajeLog(ex), ex);

        HttpClientErrorException bsException = (HttpClientErrorException) ex;
        GenericApiRsDto<String> response = new GenericApiRsDto<>(bsException.getStatusCode().value(), bsException.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {RestClientException.class})
    protected ResponseEntity<Object> handleRestClientException(RuntimeException ex, WebRequest request) {
        log.debug("handleRestClientException");
        log.error(getMensajeLog(ex), ex);

        RestClientException bsException = (RestClientException) ex;
        GenericApiRsDto<String> response = new GenericApiRsDto<>(400, bsException.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, NullPointerException.class})
    protected ResponseEntity<Object> handleArgumentsException(RuntimeException ex, WebRequest request) {
        log.debug("handleArgumentsException");
        log.error(getMensajeLog(ex), ex);

        GenericApiRsDto<String> response = new GenericApiRsDto<>(400, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {NotFoundHeroException.class})
    protected ResponseEntity<Object> handleBussinessServiceException(RuntimeException ex, WebRequest request) {
        log.debug("handleBussinessServiceException");
        // log.error(getMensajeLog(ex), ex);

        NotFoundHeroException bsException = (NotFoundHeroException) ex;
        GenericApiRsDto<String> response = new GenericApiRsDto<>(100, bsException.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    private String getMensajeLog(Exception ex) {
        return new StringBuilder()
            .append("Ocurrio una excepcion")
            .append("\n\tException= ").append(ex)
            .append("\n\tMensaje= ").append(ex.getMessage())
            .append("\n\tCausa= ").append(ex.getCause())
            .toString();
    }
}
