package cs3500.pa04.json.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.client.model.ShipDirection;

/**
 * Representation of a Ship Json object
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") ShipDirection direction) {
}
