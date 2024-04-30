package io.softwarebuilding.fusionplex.client.models.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;

public class Dates implements Serializable {
    @Serial
    private static final long serialVersionUID = 315073773994284117L;

    @JsonProperty("maximum")
    private String maximum;

    @JsonProperty("minimum")
    private String minimum;

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }
}
