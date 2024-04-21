package com.beautysalon.ui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@ControllerAdvice(basePackages = "com.beautysalon.panel")
@RequestMapping("/panel")
public class ErrorController {

    private final String DEFAULT_ERROR_VIEW = "errors/error";

    private static Logger logger =
            LoggerFactory.getLogger(ErrorController.class);


    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoResourceFound(
            NoResourceFoundException e,
            Model model
    ) {
        logger.error("No resource ", e);
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        model.addAttribute("errorMessage","Page not found.");
        model.addAttribute("httpStatus", HttpStatus.NOT_FOUND);
        return modelAndView;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @GetMapping("/error")
    public ModelAndView exception(
            final Throwable throwable) {
        logger.error("Exception during execution of SpringSecurity application", throwable);
        final String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(DEFAULT_ERROR_VIEW);
        modelAndView.addObject("errorMessage", errorMessage);
        modelAndView.addObject("httpStatusCode", HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }



}