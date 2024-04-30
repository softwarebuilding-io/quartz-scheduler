package io.softwarebuilding.fusionplex.advice;

import io.softwarebuilding.fusionplex.error.FusionPlexException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger( GlobalExceptionHandler.class );

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(
            final RuntimeException exception, final RedirectAttributes redirectAttributes, final HttpServletRequest request ) {
        LOG.error( "An error occurred", exception );

        redirectAttributes.addFlashAttribute( "alertMessage", "Oops an error occurred!" );
        redirectAttributes.addFlashAttribute( "alertType", "danger" );

        final String referer = request.getHeader( "Referer" );

        return "redirect:" + referer;
    }

    @ExceptionHandler(FusionPlexException.class)
    public String handleFusionPlexException(
            final FusionPlexException exception,
            final RedirectAttributes redirectAttributes, final HttpServletRequest request ) {
        LOG.error( "An error occurred", exception );

        redirectAttributes.addFlashAttribute( "alertMessage", exception.getMessage());
        redirectAttributes.addFlashAttribute( "alertType", "danger" );

        final String referer = request.getHeader( "Referer" );

        return "redirect:" + referer;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException exception,
            final RedirectAttributes redirectAttributes,
            final HttpServletRequest request ) {
        LOG.error( "Method Argument Not Valid Exception", exception );

        final String errorMessage = String.join( "<br>", exception.getFieldErrors().stream()
                .map( DefaultMessageSourceResolvable::getDefaultMessage ).toList() );

        redirectAttributes.addFlashAttribute( "alertMessage", errorMessage );
        redirectAttributes.addFlashAttribute( "alertType", "danger" );

        final String referer = request.getHeader( "Referer" );

        return "redirect:" + referer;
    }

    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(
            final ValidationException exception, final RedirectAttributes redirectAttributes, final HttpServletRequest request ) {
        LOG.error( "Validation Exception", exception );

        final String message = exception.getMessage();

        redirectAttributes.addFlashAttribute( "alertMessage", message );
        redirectAttributes.addFlashAttribute( "alertType", "danger" );

        final String referer = request.getHeader( "Referer" );

        return "redirect:" + referer;
    }

}
