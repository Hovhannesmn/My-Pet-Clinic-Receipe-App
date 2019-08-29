package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.exeptions.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleWrongNumberFormat(Exception exception) {
        ModelAndView modelAndView = new ModelAndView("owners/404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CustomNotFoundException.class)
    public ModelAndView notFountOwner(Exception e) {
        ModelAndView modelAndView = new ModelAndView("owners/404error");
        modelAndView.addObject("exception",  e);
        return modelAndView;
    }
}
