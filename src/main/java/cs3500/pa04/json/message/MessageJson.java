package cs3500.pa04.json.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Representation of a Json message
 */
public record MessageJson(
    @JsonProperty("method-name") String messageName,
    @JsonProperty("arguments") JsonNode arguments) {

  public String toString() {
    return JsonUtils.serializeRecord(this).toString();
  }
}
