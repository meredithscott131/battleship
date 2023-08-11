package cs3500.pa04.json.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.client.model.GameType;

/**
 * Representation of a Join Json message
 */
public record JoinJson(
    @JsonProperty("name") String username,
    @JsonProperty("game-type") GameType type) {
}
