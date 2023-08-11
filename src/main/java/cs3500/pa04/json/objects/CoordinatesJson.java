package cs3500.pa04.json.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representation of a Coord Json object
 */
public record CoordinatesJson(
    @JsonProperty("coordinates") CoordJson[] coords) {
}
