package io.softwarebuilding.fusionplex.client.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.softwarebuilding.fusionplex.client.models.error.ErrorResponse;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;

public final class ApiUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private ApiUtil() {
    }

    public static String toJson(final Object object) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (final JsonProcessingException exception) {
            return "{\"status_code\":500,\"status_message\":\"Failed to convert error response to JSON\",\"success\":false}";
        }
    }

    public static ErrorResponse parseError(Response<?> response) throws IOException {

        final ResponseBody responseBody = response.errorBody();

        if (responseBody != null) {

            final String json = responseBody.string();

            try (responseBody) {
                return mapper.readValue(json, ErrorResponse.class);
            } catch (IOException e) {
                return new ErrorResponse("Failed to read error response", 500);
            }

        } else {
            return new ErrorResponse("Unknown error", response.code());
        }
    }

}
