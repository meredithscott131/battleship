package cs3500.pa04.json.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.objects.CoordinatesJson;

/**
 * Representation of a TakeShots Json message
 */
public record TakeShotsJson(
    @JsonProperty("coordinates") CoordinatesJson coords) {
}
