package io.softwarebuilding.fusionplex.config;

import io.softwarebuilding.fusionplex.interceptor.WebClientInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class OkHttpClientConfig {

    private final FusionPlexProperties properties;

    @Autowired
    public OkHttpClientConfig(FusionPlexProperties properties) {
        this.properties = properties;
    }

    @Bean
    public OkHttpClient okHttpClient() {
        final WebClientInterceptor interceptor = new WebClientInterceptor(this.properties);

        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Bean
    public Retrofit retrofit(final OkHttpClient okHttpClient) {
        final String baseUrl = properties.baseUri();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
