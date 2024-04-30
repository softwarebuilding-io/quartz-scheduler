package io.softwarebuilding.fusionplex.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fusion-plex")
public record FusionPlexProperties(String baseUri, String bearerToken) {
}
