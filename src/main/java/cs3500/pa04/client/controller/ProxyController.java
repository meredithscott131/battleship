package cs3500.pa04.client.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import cs3500.pa04.client.model.AiPlayer;
import cs3500.pa04.client.model.Coord;
import cs3500.pa04.client.model.GameType;
import cs3500.pa04.client.model.Ship;
import cs3500.pa04.client.model.ShipAdapter;
import cs3500.pa04.client.model.ShipType;
import cs3500.pa04.json.message.JoinJson;
import cs3500.pa04.json.message.JsonUtils;
import cs3500.pa04.json.message.MessageJson;
import cs3500.pa04.json.message.SuccessfulHitsJson;
import cs3500.pa04.json.objects.CoordJson;
import cs3500.pa04.json.objects.CoordinatesJson;
import cs3500.pa04.json.objects.FleetJson;
import cs3500.pa04.json.objects.ShipJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a proxy controller for the BattleSalvo game
 */
public class ProxyController implements BattleSalvoController {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final AiPlayer player;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Instantiates a new Proxy controller for testing.
   *
   * @param server the server
   * @param player the AI player
   */
  public ProxyController(Socket server, AiPlayer player) {
    this.server = server;
    this.player = player;
    try {
      this.in = server.getInputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    try {
      this.out = new PrintStream(server.getOutputStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Play the BattleSalvo game.
   */
  @Override
  public void play() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);
      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        String str = delegateMessage(message);
        if (str.equals("end-game")) {
          this.server.close();
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Determines the request the server has sent and delegates to
   * the corresponding method with the message arguments
   */
  private String delegateMessage(MessageJson message) {
    String name = message.messageName();
    JsonNode arguments = message.arguments();

    if (name.equals("join")) {
      this.handleJoin();
    } else if (name.equals("setup")) {
      this.handleSetup(arguments);
    } else if (name.equals("take-shots")) {
      this.handleTakeShots();
    } else if (name.equals("report-damage")) {
      this.handleReportDamage(arguments);
    } else if (name.equals("successful-hits")) {
      this.handleSuccess();
    } else if (name.equals("end-game")) {
      this.handleEndGame();
    } else {
      throw new IllegalStateException("Invalid message name");
    }
    return name;
  }

  /**
   * Determines the request the server has sent and delegates to
   * the corresponding method with the message arguments
   */
  private void handleJoin() {
    JoinJson joinJson = new JoinJson("LalCelik", GameType.SINGLE);
    JsonNode jsonResponse = JsonUtils.serializeRecord(joinJson);

    MessageJson message = new MessageJson("join", jsonResponse);
    JsonNode jsonMessage = JsonUtils.serializeRecord(message);
    this.out.println(jsonMessage);
  }

  /**
   * Parses the given arguments as a Setup message and returns the
   * player's setup response.
   */
  private void handleSetup(JsonNode arguments) {
    JsonNode widthJson = arguments.get("width");
    int width = mapper.convertValue(widthJson, Integer.class);

    JsonNode heightJson = arguments.get("height");
    int height = mapper.convertValue(heightJson, Integer.class);

    JsonNode fleetJsonNode = arguments.get("fleet-spec");
    Map<ShipType, Integer> mapOfFleet = makeFleetMap(fleetJsonNode);

    List<Ship> listShips = player.setup(height, width, mapOfFleet);

    player.getData().setShips(listShips);

    FleetJson fleetJson = new FleetJson(shipArray(listShips));

    JsonNode jsonResponse = JsonUtils.serializeRecord(fleetJson);

    MessageJson message = new MessageJson("setup", jsonResponse);

    JsonNode finalMessage = JsonUtils.serializeRecord(message);

    this.out.println(finalMessage);
  }


  /**
   * Given a list of ships, converts each one to ship jsons and returns them in an array
   *
   * @param listShips the list ships
   * @return the ship json [ ]
   */
  public ShipJson[] shipArray(List<Ship> listShips) {
    ShipJson[] shipArray = new ShipJson[this.player.getData().getBoard().getShips().size()];
    for (int i = 0; i < shipArray.length; i++) {
      Ship s = listShips.get(i);
      ShipAdapter shipAdapter = new ShipAdapter(s);
      ShipJson shipJson = shipAdapter.convertToShipJson();
      shipArray[i] = shipJson;
    }
    return shipArray;
  }


  /**
   * Given a list of ships, converts each one to ship jsons and returns them in an array
   *
   * @param fleetJsonNode JsonNode of a fleet of ships
   * @return a hashmap containing ship specifications for a fleet
   */
  private Map<ShipType, Integer> makeFleetMap(JsonNode fleetJsonNode) {
    Map<ShipType, Integer> map = new HashMap<>();

    JsonNode carrierJsonNode = fleetJsonNode.get("CARRIER");
    int carrierJson = mapper.convertValue(carrierJsonNode, Integer.class);
    map.put(ShipType.CARRIER, carrierJson);

    JsonNode battleShipJsonNode = fleetJsonNode.get("BATTLESHIP");
    int battleShipJson = mapper.convertValue(battleShipJsonNode, Integer.class);
    map.put(ShipType.BATTLESHIP, battleShipJson);

    JsonNode destroyerJsonNode = fleetJsonNode.get("DESTROYER");
    int destroyerJson = mapper.convertValue(destroyerJsonNode, Integer.class);
    map.put(ShipType.DESTROYER, destroyerJson);

    JsonNode submarineJsonNode = fleetJsonNode.get("SUBMARINE");
    int submarineJson = mapper.convertValue(submarineJsonNode, Integer.class);
    map.put(ShipType.SUBMARINE, submarineJson);

    return map;
  }

  /**
   * Parses the given arguments as a TakeShots message and returns the
   * player's TakeShots response.
   */
  private void handleTakeShots() {

    List<Coord> shots = this.player.takeShots();
    CoordJson[] coords = new CoordJson[shots.size()];
    for (int i = 0; i < shots.size(); i++) {
      coords[i] = new CoordJson(shots.get(i).getX(), shots.get(i).getY());
    }

    CoordinatesJson coordList = new CoordinatesJson(coords);
    JsonNode jsonResponse = JsonUtils.serializeRecord(coordList);
    MessageJson message = new MessageJson("take-shots", jsonResponse);

    JsonNode finalMessage = JsonUtils.serializeRecord(message);

    this.out.println(finalMessage);
  }

  /**
   * Parses the given arguments as a ReportDamage message and returns the
   * player's ReportDamage response.
   */
  private void handleReportDamage(JsonNode arguments) {
    JsonNode coordinates = arguments.get("coordinates");
    CoordJson[] coordList = mapper.convertValue(coordinates, CoordJson[].class);

    List<Coord> shots = new ArrayList<>();

    for (CoordJson coordJson : coordList) {
      Coord c = new Coord(coordJson.xcoord(), coordJson.ycoord());
      shots.add(c);
    }

    // filters successful hits
    List<Coord> damage = this.player.reportDamage(shots);
    this.player.getData().getBoard().updateBoard(shots);

    CoordJson[] damageJs = new CoordJson[damage.size()];
    for (int i = 0; i < damage.size(); i++) { // converts to CoordJson
      CoordJson c = new CoordJson(damage.get(i).getX(), damage.get(i).getY());
      damageJs[i] = c;
    }

    CoordinatesJson reportDamageJson = new CoordinatesJson(damageJs);

    JsonNode jsonResponse = JsonUtils.serializeRecord(reportDamageJson);
    MessageJson message = new MessageJson("report-damage", jsonResponse);

    JsonNode finalMessage = JsonUtils.serializeRecord(message);
    this.out.println(finalMessage);
  }

  /**
   * Parses the given arguments as a SuccessfulHits message and returns the
   * player's SuccessfulHits response.
   */
  private void handleSuccess() {
    SuccessfulHitsJson successfulHitsJson = new SuccessfulHitsJson();
    JsonNode jsonResponse = JsonUtils.serializeRecord(successfulHitsJson);
    MessageJson message = new MessageJson("successful-hits", jsonResponse);
    this.out.println(message);
  }

  /**
   * Parses the given arguments as an Endgame message and returns the
   * player's EndGame response.
   */
  private void handleEndGame() {
    JsonNode jsonResponse = JsonNodeFactory.instance.objectNode();

    MessageJson message = new MessageJson("end-game", jsonResponse);
    JsonNode finalMessage = JsonUtils.serializeRecord(message);

    this.out.println(finalMessage);
  }
}