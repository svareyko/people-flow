package com.reconnect.web.peopleflow.web;

import com.reconnect.web.peopleflow.exceptions.MissingStateException;
import com.reconnect.web.peopleflow.exceptions.RequiredEmployeeAsReturnException;
import com.reconnect.web.peopleflow.exceptions.StateUpdateException;
import com.reconnect.web.peopleflow.exceptions.UsernameNotProvidedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author s.vareyko
 * @since 01.04.2021
 */
@Slf4j
@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler({
            StateUpdateException.class,
            MissingStateException.class
    })
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public String stateUpdateException(final Exception exception) {
        log.warn(exception.getMessage());
        return exception.getMessage();
    }

    @ExceptionHandler({
            RequiredEmployeeAsReturnException.class,
            UsernameNotProvidedException.class
    })
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String adviceExceptions(final Exception exception) {
        log.error(exception.getMessage());
        return exception.getMessage();
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String commonExceptionHandler(final Throwable throwable) {
        log.error(throwable.getMessage());
        throwable.printStackTrace();
        return throwable.getMessage();
    }
}
