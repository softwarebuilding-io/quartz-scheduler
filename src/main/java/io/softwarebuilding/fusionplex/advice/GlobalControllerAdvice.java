package io.softwarebuilding.fusionplex.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("globalTitle")
    public String getGlobalTitle() {
        return "Movie, TV Series, Documentary Catalogue Management";
    }

}
