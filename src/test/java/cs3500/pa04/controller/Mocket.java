package cs3500.pa04.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;

/**
 * Mock Socket for testing server connection
 */
public class Mocket extends Socket {
  private final InputStream testInputs;
  private final ByteArrayOutputStream testLog;

  /**
   * Instantiates a new Mocket.
   *
   * @param testLog what the server has received from the client
   * @param toSend  what the server will send to the client
   */
  public Mocket(ByteArrayOutputStream testLog, List<String> toSend) {
    this.testLog = testLog;

    // Set up the list of inputs as separate messages of JSON for the ProxyDealer to handle
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    for (String message : toSend) {
      printWriter.println(message);
    }
    this.testInputs = new ByteArrayInputStream(stringWriter.toString().getBytes());
  }

  /**
   *
   * @return the input stream
   */
  @Override
  public InputStream getInputStream() {
    return this.testInputs;
  }

  /**
   *
   * @return the output stream
   */
  @Override
  public OutputStream getOutputStream() {
    return this.testLog;
  }
}
