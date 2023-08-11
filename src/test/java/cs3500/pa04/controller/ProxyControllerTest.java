package cs3500.pa04.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.client.controller.ProxyController;
import cs3500.pa04.client.model.AiPlayer;
import cs3500.pa04.client.model.GameResult;
import cs3500.pa04.client.model.GameType;
import cs3500.pa04.client.model.ShipType;
import cs3500.pa04.json.message.EndGameJson;
import cs3500.pa04.json.message.JoinJson;
import cs3500.pa04.json.message.JsonUtils;
import cs3500.pa04.json.message.MessageJson;
import cs3500.pa04.json.message.SetupJsonRequest;
import cs3500.pa04.json.message.TakeShotsJson;
import cs3500.pa04.json.objects.CoordJson;
import cs3500.pa04.json.objects.CoordinatesJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the ProxyController class.
 */
public class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController controller;

  /**
   * Innit.
   */
  @BeforeEach
  public void innit() {
    this.testLog = new ByteArrayOutputStream(10000);
    assertEquals("", logToString());
  }

  /**
   * Test join json message.
   */
  @Test
  public void testJoin() {
    JoinJson joinJson = new JoinJson("LalCelik", GameType.SINGLE);
    JsonNode joinNode = createSampleMessage("join", joinJson);

    EndGameJson endGameJson = new EndGameJson(GameResult.LOSE, "You lost!");
    JsonNode endGameNode = createSampleMessage("end-game", endGameJson);

    Mocket socket = new Mocket(this.testLog, List.of(joinNode.toString(),
        endGameNode.toString()));

    this.controller = new ProxyController(socket, new AiPlayer(1));

    String expected = "{\"method-name\":\"join\",\"arguments\":{\"name\":\"LalCelik\","
        + "\"game-type\":\"SINGLE\"}}{\"method-name\":\"end-game\",\"arguments\":{}}";

    this.controller.play();

    assertEquals(expected.trim().replaceAll("[\n\r]+", ""),
        logToString().trim().replaceAll("[\n\r]+", ""));
    responseToClass(MessageJson.class);
  }

  /**
   * Test setup json message.
   */
  @Test
  public void testSetup() {
    SetupJsonRequest setupRequest = new SetupJsonRequest(6, 6,
        this.generateFleet(1, 2, 1, 2));
    JsonNode setupNode = createSampleMessage("setup", setupRequest);

    EndGameJson endGameJson = new EndGameJson(GameResult.LOSE, "You lost!");
    JsonNode endGameNode = createSampleMessage("end-game", endGameJson);

    Mocket socket = new Mocket(this.testLog, List.of(setupNode.toString(), endGameNode.toString()));
    this.controller = new ProxyController(socket, new AiPlayer(1));
    String expected = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\":0,"
        + "\"y\":4},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":2},\""
        + "length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":1,\"y\":3},\"length\":5,"
        + "\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":0},\"length\":4,\"direction\":"
        + "\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":1},\"length\":3,\"direction\":\"HORIZONTAL\"}"
        + ",{\"coord\":{\"x\":3,\"y\":5},\"length\":3,\"direction\":\"HORIZONTAL\"}]}}{\""
        + "method-name\":\"end-game\",\"arguments\":{}}";
    this.controller.play();
    assertEquals(expected.trim().replaceAll("[\n\r]+", ""),
        logToString().trim().replaceAll("[\n\r]+", ""));
    responseToClass(MessageJson.class);

  }

  /**
   * Test take-shots json message.
   */
  @Test
  public void testTakeShots() {
    SetupJsonRequest setupRequest = new SetupJsonRequest(6, 6,
        this.generateFleet(3, 1, 1, 1));
    JsonNode setupNode = createSampleMessage("setup", setupRequest);

    JsonNode takeShotsNode = createSampleMessage("take-shots",
        new TakeShotsJson(new CoordinatesJson(new CoordJson[]{})));

    EndGameJson endGameJson = new EndGameJson(GameResult.LOSE, "You lost!");
    JsonNode endGameNode = createSampleMessage("end-game", endGameJson);

    Mocket socket = new Mocket(this.testLog, List.of(setupNode.toString(),
        takeShotsNode.toString(), endGameNode.toString()));
    this.controller = new ProxyController(socket, new AiPlayer(1));
    String expected = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\""
        + ":{\"x\":0,\"y\":4},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":{\""
        + "x\":0,\"y\":2},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,"
        + "\"y\":0},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":1},"
        + "\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":3},\"length\""
        + ":4,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":3,\"y\":5},\"length\":3,\"direction"
        + "\":\"HORIZONTAL\"}]}}{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\""
        + ":[{\"x\":3,\"y\":4},{\"x\":1,\"y\":3},{\"x\":2,\"y\":4},{\"x\":4,\"y\":4},{\"x\":1,\""
        + "y\":1},{\"x\":0,\"y\":4}]}}{\"method-name\":\"end-game\",\"arguments\":{}}";
    this.controller.play();
    assertEquals(expected.trim().replaceAll("[\n\r]+", ""),
        logToString().trim().replaceAll("[\n\r]+", ""));
    responseToClass(MessageJson.class);
  }

  /**
   * Test report-damage json message.
   */
  @Test
  public void testReportDamage() {
    CoordJson[] damageArr = new CoordJson[3];
    damageArr[0] = new CoordJson(0, 0);
    damageArr [1] = new CoordJson(0, 1);
    damageArr [2] = new CoordJson(0, 2);
    CoordinatesJson damage = new CoordinatesJson(damageArr);
    JsonNode damageNode = createSampleMessage("report-damage", damage);

    EndGameJson endGameJson = new EndGameJson(GameResult.LOSE, "You lost!");
    JsonNode endGameNode = createSampleMessage("end-game", endGameJson);

    SetupJsonRequest setupRequest = new SetupJsonRequest(6, 6,
        this.generateFleet(1, 2, 2, 1));
    JsonNode setupNode = createSampleMessage("setup", setupRequest);

    JsonNode takeShotsNode = createSampleMessage("take-shots",
        new TakeShotsJson(new CoordinatesJson(new CoordJson[]{})));

    Mocket socket = new Mocket(this.testLog, List.of(setupNode.toString(),
        takeShotsNode.toString(), damageNode.toString(), endGameNode.toString()));
    this.controller = new ProxyController(socket, new AiPlayer(1));
    String expected = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\""
        + "x\":0,\"y\":4},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":2},"
        + "\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":1,\"y\":3},\"length\":5,"
        + "\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":0},\"length\":4,\"direction\":"
        + "\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":1},\"length\":4,\"direction\":\"HORIZONTAL\"},"
        + "{\"coord\":{\"x\":3,\"y\":5},\"length\":3,\"direction\":\"HORIZONTAL\"}]}}{\"method-"
        +  "name\":\"take-shots\",\"arguments\":{\"coordinates\":[{\"x\":3,\"y\":4},{\"x\":1,\"y\":"
        + "3},{\"x\":2,\"y\":4},{\"x\":4,\"y\":4},{\"x\":1,\"y\":1},{\"x\":0,\"y\":4}]}}{\"method-n"
        + "ame\":\"report-damage\",\"arguments\":{\"coordinates\":[{\"x\":0,\"y\":0},{\"x\":0,\"y\""
        + ":1},{\"x\":0,\"y\":2}]}}{\"method-name\":\"end-game\",\"arguments\":{}}";
    this.controller.play();
    assertEquals(expected.trim().replaceAll("[\n\r]+", ""),
        logToString().trim().replaceAll("[\n\r]+", ""));
    responseToClass(MessageJson.class);
  }

  /**
   * Test successful-hits json message.
   */
  @Test
  public void testSuccessfulHits() {
    CoordJson[] damageArr = new CoordJson[3];
    damageArr[0] = new CoordJson(0, 0);
    damageArr [1] = new CoordJson(0, 1);
    damageArr [2] = new CoordJson(0, 2);
    CoordinatesJson damage = new CoordinatesJson(damageArr);
    JsonNode damageNode = createSampleMessage("report-damage", damage);

    CoordJson[] hitArr = new CoordJson[2];
    hitArr[0] = new CoordJson(0, 1);
    hitArr[1] = new CoordJson(0, 2);
    CoordinatesJson hits = new CoordinatesJson(hitArr);
    JsonNode hitsNode = createSampleMessage("successful-hits", hits);

    EndGameJson endGameJson = new EndGameJson(GameResult.LOSE, "You lost!");
    JsonNode endGameNode = createSampleMessage("end-game", endGameJson);

    SetupJsonRequest setupRequest = new SetupJsonRequest(6, 6,
        this.generateFleet(1, 1, 1, 1));
    JsonNode setupNode = createSampleMessage("setup", setupRequest);

    JsonNode takeShotsNode = createSampleMessage("take-shots",
        new TakeShotsJson(new CoordinatesJson(new CoordJson[]{})));

    Mocket socket = new Mocket(this.testLog, List.of(setupNode.toString(),
        takeShotsNode.toString(), damageNode.toString(),
        hitsNode.toString(), endGameNode.toString()));
    this.controller = new ProxyController(socket, new AiPlayer(1));
    String expected = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\""
        + ":0,\"y\":4},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":2},"
        + "\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":5,\"y\":0},\"length\":4,"
        + "\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":2,\"y\":5},\"length\":3,\"direction\":\""
        + "HORIZONTAL\"}]}}\n{\"method-name\":\"take-shots\",\"arguments\":{\"coordinates\":[{\"x\""
        + ":3,\"y\":4},{\"x\":1,\"y\":3},{\"x\":2,\"y\":4},{\"x\":4,\"y\":4}]}}\n{\"method-name\""
        + ":\"report-damage\",\"arguments\":{\"coordinates\":[{\"x\":0,\"y\":2}]}}\n{\"method-name"
        + "\":\"successful-hits\",\"arguments\":{}}\n{\"method-name\":\"end-game\","
        + "\"arguments\":{}}\n";
    this.controller.play();
    assertEquals(expected.trim().replaceAll("[\n\r]+", ""),
        logToString().trim().replaceAll("[\n\r]+", ""));
    responseToClass(MessageJson.class);
  }

  /**
   * Test end-game json message.
   */
  @Test
  public void testEndGame() {
    EndGameJson endGameJson = new EndGameJson(GameResult.WIN, "You won!");
    JsonNode endGameNode = createSampleMessage("end-game", endGameJson);

    Mocket socket = new Mocket(this.testLog, List.of(endGameNode.toString()));

    String expected = "{\"method-name\":\"end-game\",\"arguments\":{}}";

    this.controller = new ProxyController(socket, new AiPlayer(1));
    this.controller.play();
    assertEquals(expected.trim().replaceAll("[\n\r]+", ""),
        logToString().trim().replaceAll("[\n\r]+", ""));
    responseToClass(MessageJson.class);
  }

  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName name of the type of message; "hint" or "win"
   * @param messageObject object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
        JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T>      Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      try (JsonParser jsonParser = new ObjectMapper().createParser(logToString())) {
        jsonParser.readValueAs(classRef);
      }
    } catch (IOException e) {
      fail();
    }
  }

  /**
   * Generates a fleet of ships.
   *
   * @param carrInt number of Carrier ships
   * @param battleInt number of Battleship ships
   * @param destInt number of destroyer ships
   * @param subInt number of submarine ships
   * @return map of fleet specifications
   */
  private Map<ShipType, Integer> generateFleet(int carrInt, int battleInt,
                                               int destInt, int subInt) {
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, carrInt);
    specs.put(ShipType.BATTLESHIP, battleInt);
    specs.put(ShipType.DESTROYER, destInt);
    specs.put(ShipType.SUBMARINE, subInt);

    return specs;
  }
}