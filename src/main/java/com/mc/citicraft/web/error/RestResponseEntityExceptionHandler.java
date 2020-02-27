package com.mc.citicraft.web.error;

import com.mc.citicraft.web.exception.BadRequestException;
import com.mc.citicraft.web.exception.FileStorageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(FileStorageException.class)
    protected ModelAndView handleStorageException(RuntimeException ex, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "TBD");
        mav.setViewName("error");
        return mav;
    }
}
