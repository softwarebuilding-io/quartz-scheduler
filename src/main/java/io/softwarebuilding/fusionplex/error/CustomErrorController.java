package io.softwarebuilding.fusionplex.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError( final Model model, final HttpServletRequest request ) {
        final Object status = request.getAttribute( RequestDispatcher.ERROR_STATUS_CODE );

        if ( status != null ) {
            final int statusCode = Integer.parseInt( status.toString() );

            if ( statusCode == HttpStatus.NOT_FOUND.value() ) {
                model.addAttribute( "globalTitle", "404 Not Found" );
                return "/error/404";
            } else if ( statusCode == HttpStatus.BAD_REQUEST.value() ) {
                model.addAttribute( "globalTitle", "400 Bad Request" );
                return "error/400";
            }
        }

        model.addAttribute( "globalTitle", "Internal Server Error" );
        return "error/500";
    }

    public String getErrorPath() {
        return "/error";
    }

}
