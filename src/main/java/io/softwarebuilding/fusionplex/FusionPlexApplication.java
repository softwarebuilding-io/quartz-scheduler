package io.softwarebuilding.fusionplex;

import io.softwarebuilding.fusionplex.config.FusionPlexProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FusionPlexProperties.class)
public class FusionPlexApplication {

    public static void main( final String[] args ) {
        SpringApplication.run( FusionPlexApplication.class, args );
    }

}
