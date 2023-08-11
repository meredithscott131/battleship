package cs3500.pa04.json.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.objects.FleetJson;

/**
 * Representation of a Setup Json message.
 */
public record SetupJson(
    @JsonProperty("fleet") FleetJson fleet){
}
