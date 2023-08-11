package cs3500.pa04.json.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa04.client.model.GameResult;

/**
 * Representation of an EndGame Json message
 */
public record EndGameJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason){
}