package cs3500.pa04.json.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.client.model.ShipType;
import java.util.Map;

/**
 * Represents a Setup request from the server.
 */
public record SetupJsonRequest(
    @JsonProperty("height") int height,
    @JsonProperty("width") int width,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec){
}
