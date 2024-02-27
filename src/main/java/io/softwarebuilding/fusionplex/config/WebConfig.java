package io.softwarebuilding.fusionplex.config;

import io.softwarebuilding.fusionplex.converter.StringToGenreDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToGenreDtoConverter stringToGenreDtoConverter;

    @Autowired
    public WebConfig( final StringToGenreDtoConverter stringToGenreDtoConverter ) {
        this.stringToGenreDtoConverter = stringToGenreDtoConverter;
    }

    @Override
    public void addFormatters( final FormatterRegistry registry ) {
        registry.addConverter( this.stringToGenreDtoConverter );
    }

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
        final FilterRegistrationBean<HiddenHttpMethodFilter> filterRegistrationBean = new FilterRegistrationBean<>( new HiddenHttpMethodFilter() );
        filterRegistrationBean.addUrlPatterns( "/*" );

        return filterRegistrationBean;
    }


}
