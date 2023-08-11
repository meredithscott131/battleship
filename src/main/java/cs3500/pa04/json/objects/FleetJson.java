package cs3500.pa04.json.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of a Fleet Json object
 */
public record FleetJson(
    @JsonProperty("fleet") ShipJson[] ships) {
}
