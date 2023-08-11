package cs3500.pa04.json.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.json.objects.CoordinatesJson;

/**
 * Representation of a ReportDamage Json message
 */
public record ReportDamageJson(
    @JsonProperty("coordinates") CoordinatesJson coords){
}
