package io.softwarebuilding.fusionplex.interceptor;

import io.softwarebuilding.fusionplex.config.FusionPlexProperties;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WebClientInterceptor implements Interceptor {

    private final FusionPlexProperties properties;

    @Autowired
    public WebClientInterceptor(final FusionPlexProperties properties) {
        this.properties = properties;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Request newRequest = originalRequest.newBuilder()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, this.getToken()).build();

        return chain.proceed(newRequest);
    }

    public String getToken() {
        return "Bearer " + this.properties.bearerToken();
    }
}
