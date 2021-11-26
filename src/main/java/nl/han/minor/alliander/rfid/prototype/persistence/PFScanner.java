package nl.han.minor.alliander.rfid.prototype.persistence;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;

//https://openjdk.java.net/groups/net/httpclient/intro.html
//https://mkyong.com/java/how-to-send-http-request-getpost-in-java/

public class PFScanner implements IScanner {
  private String bearerToken = null;
  private String baseURL = "http://192.168.1.10/api/";
  private String loginName = "admin";
  private String loginPassword = "b0fc-4801-4a30-8d39-2c01-6b6a";
  private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).build();
  private JSONParser parser = new JSONParser();

  private boolean setContinous = false;

  @Override
  public List<BigInteger> scanTags() {
    List<BigInteger> tagIDs = new ArrayList<>();
    try {
      if (!setContinous) {
        setContinous();
        setContinous = true;
      }
      JSONArray tags = readDataContinous();
      for (Object o : tags) {
        if (o instanceof JSONObject) {
          if (((JSONObject) o).containsKey("UII")) {
            tagIDs.add(new BigInteger((String) ((JSONObject) o).get("UII"), 16));
          }
        }
      }
    } catch (Exception e) {
      System.err.println(e);
    }

    return tagIDs;
  }

  @Override
  public boolean startScan() {
    try {
      setContinous();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("error");
      return false;
    }
    return true;
  }

  @Override
  public boolean stopScan() {
    try {
      stopContinous();
      logout();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
  /**
   * @return List<long>
   */
  // @Override
  // public List<long> scanTags() {

  // }

  /**
   * Returns the device identification
   * 
   * @throws Exception
   */
  private void identification() throws Exception {
    HttpRequest request = createGetRequest("identification");
    sendRequest(request);
  }

  /**
   * Return all antenna configurations
   * 
   * @throws Exception
   */
  private void antennas() throws Exception {
    HttpRequest request = createGetRequest("rfid/antennas");
    sendRequest(request);
  }

  /**
   * Get input and output configurations
   * 
   * @throws Exception
   */
  private void getIOconfig() throws Exception {
    HttpRequest request = createGetRequest("rfid/io");
    sendRequest(request);
  }

  /**
   * Return all input configurations
   * 
   * @throws Exception
   */
  private void getInputs() throws Exception {
    HttpRequest request = createGetRequest("rfid/io/inputs");
    sendRequest(request);
  }

  /**
   * Return all output configurations
   * 
   * @throws Exception
   */
  private void getOutputs() throws Exception {
    HttpRequest request = createGetRequest("rfid/io/outputs");
    sendRequest(request);
  }

  /**
   * Get the read transponder configuration
   * 
   * @throws Exception
   */
  private void readConfig() throws Exception {
    HttpRequest request = createGetRequest("rfid/transponder/read");
    sendRequest(request);

  }

  /**
   * Return transponder data from continous read (rfid/transponder/read/data)
   * 
   * @throws Exception
   * @return JSONArray
   */
  private JSONArray readData() throws Exception {
    HttpRequest request = createPutRequest("rfid/transponder/read/data",
        "{\"ids\":[1],\"execution_type\":\"SINGLE\",\"memory\":{\"bank\":\"USER\",\"block_address\":0},\"length\":8}");
    HttpResponse<String> response = sendRequest(request);

    Object jsonO = ((JSONArray) parser.parse(response.body())).get(0);
    if (jsonO instanceof JSONObject) {
      JSONObject json = (JSONObject) jsonO;
      if (json.containsKey("readings")) {
        if (json.get("readings") instanceof JSONArray) {
          return (JSONArray) json.get("readings");
        }
      }
    }
    return null;

  }

  /**
   * Set transponder data to continous read (rfid/transponder/read)
   * 
   * @throws Exception
   * @return JSONArray
   */
  private void setContinous() throws Exception {
    HttpRequest request = createPutRequest("rfid/transponder/read",
        "{\"ids\":[1],\"execution_type\":\"CONTINUOUS\",\"memory\":{\"bank\":\"USER\",\"block_address\":0},\"length\":8}");
    HttpResponse<String> response = sendRequest(request);
  }

  /**
   * Set transponder data to stop continous read (rfid/transponder/read)
   * 
   * @throws Exception
   * @return JSONArray
   */
  private void stopContinous() throws Exception {
    HttpRequest request = createPutRequest("rfid/transponder/read",
        "{\"ids\":[1],\"execution_type\":\"STOP\",\"memory\":{\"bank\":\"USER\",\"block_address\":0},\"length\":8}");
    HttpResponse<String> response = sendRequest(request);
  }

  /**
   * Return transponder data from continous read (rfid/transponder/read/data)
   * 
   * @throws Exception
   * @return JSONArray
   */
  private JSONArray readDataContinous() throws Exception {
    HttpRequest request = createGetRequest("rfid/transponder/read/data");
    HttpResponse<String> response = sendRequest(request);

    Object jsonO = ((JSONArray) parser.parse(response.body())).get(0);
    if (jsonO instanceof JSONObject) {
      JSONObject json = (JSONObject) jsonO;
      if (json.containsKey("readings")) {
        if (json.get("readings") instanceof JSONArray) {
          return (JSONArray) json.get("readings");
        }
      }
    }
    return null;
  }

  /**
   * Set transponder data to continous read (rfid/transponder/read)
   * 
   * @throws Exception
   * @return JSONArray
   */
  private void setContinousStop() throws Exception {
    HttpRequest request = createPutRequest("rfid/transponder/read",
        "{\"ids\":[1],\"execution_type\":\"STOP\",\"memory\":{\"bank\":\"USER\",\"block_address\":0},\"length\":8}");
    HttpResponse<String> response = sendRequest(request);
  }

  /**
   * Get the write transponder configuration
   * 
   * @throws Exception
   */
  private void writeConfiguration() throws Exception {
    HttpRequest request = createGetRequest("rfid/transponder/write");
    sendRequest(request);
  }

  /**
   * Return transponder data from continous write
   * 
   * @throws Exception
   */
  private void readDataFromContinousWrite() throws Exception {
    HttpRequest request = createGetRequest("rfid/transponder/write/data");
    sendRequest(request);
  }

  /**
   * Get kill transponder configuration
   * 
   * @throws Exception
   */
  private void readKillConfiguration() throws Exception {
    HttpRequest request = createGetRequest("rfid/transponder/kill");
    sendRequest(request);
  }

  /**
   * @throws Exception
   */
  private void systemEvents() throws Exception {
    HttpRequest request = createGetRequest("system/events");
    sendRequest(request);
  }

  /**
   * Create a HTTPRequest object for a get request
   * 
   * @param url The relative URL (so without the base url) to the endpoint
   * @return HttpRequest A Object for a get request to the correct endpoint
   */
  private HttpRequest createGetRequest(String url) {
    return HttpRequest.newBuilder().GET().uri(URI.create(baseURL + url))
        .setHeader("User-Agent", "Java 11 HttpClient Bot").header("Authorization", "Bearer " + getBearerToken())
        .build();
  }

  /**
   * Send the request
   * 
   * @param request
   * @return HttpResponse<String>
   * @throws IOException
   * @throws InterruptedException
   */
  private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
    // printRequest(request);
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    // printRequestResult(response);
    return response;
  }

  /**
   * Prints the URI from the http request
   * 
   * @param request
   */
  private void printRequest(HttpRequest request) {
    // print URI
    System.out.println("URI:\n " + request.uri());
    // print Header
    System.out.println("Headers:");
    for (var entry : request.headers().map().entrySet()) {
      System.out.println(entry.getKey() + " :: " + entry.getValue());
    }
  }

  /**
   * Prints the result of the request
   * 
   * @param response
   */
  private void printRequestResult(HttpResponse<String> response) {
    // print status code
    System.out.println("HTTP response code:\n " + response.statusCode());
    // print response body
    System.out.println("Resonse body:\n " + (response.body().isEmpty() ? "empty" : response.body()));
  }

  /**
   * Create a HttpRequest object for a post request
   * 
   * @param url  The relative URL (so without the base url) to the endpoint
   * @param body
   * @return HttpRequest
   */
  private HttpRequest createPostRequest(String url, String body) {
    return HttpRequest.newBuilder().POST(BodyPublishers.ofString(body)).uri(URI.create(baseURL + url))
        .setHeader("User-Agent", "Java 11 HttpClient Bot").header("Authorization", "Bearer " + getBearerToken())
        .header("Content-Type", "application/json").build();
  }

  /**
   * Create Put HttpRequest object for a put request
   * 
   * @param url  The relative URL (so without the base url) to the endpoint
   * @param body
   * @return HttpRequest
   */
  private HttpRequest createPutRequest(String url, String body) {
    return HttpRequest.newBuilder().PUT(BodyPublishers.ofString(body)).uri(URI.create(baseURL + url))
        .setHeader("User-Agent", "Java 11 HttpClient Bot").header("Authorization", "Bearer " + getBearerToken())
        .header("Content-Type", "application/json").build();
  }

  /**
   * @return String
   */
  private String getBearerToken() {
    if (bearerToken != null) {
      return bearerToken;
      // TODO implement renew API call if bearer has expired
    } else {
      try {
        String token = login();
        if (token != null) {
          bearerToken = token;
          return token;
        } else
          throw new Exception("Unable to login to the P+F scanner");
      } catch (Exception e) {
        System.err.println(e);
      }
    }
    return null;
  }

  /**
   * Log in to the system
   * 
   * @return String bearer token for the P+F scanner
   * @throws Exception
   */
  private String login() throws Exception {
    System.out.println("Prepairing login");
    HttpRequest request = HttpRequest.newBuilder()
        .POST(BodyPublishers.ofString("{ \"username\":\"" + loginName + "\",\"password\":\"" + loginPassword + "\" }"))
        .uri(URI.create(baseURL + "auth/login")).setHeader("User-Agent", "Java 11 HttpClient Bot")
        .header("Content-Type", "application/json").build();
    // printRequest(request);
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    // printRequestResult(response);

    System.out.println("inlog attempt HTTP code: " + response.statusCode());
    JSONObject json = (JSONObject) parser.parse(response.body());
    if (json.containsKey("token"))
      return json.get("token").toString();
    return null;
  }

  /**
   * Log out from the system (I don't know if this even does anything as the
   * bearer code still works after logout.)
   * 
   * @throws Exception
   */
  private void logout() throws Exception {
    HttpRequest request = createPostRequest("auth/logout",
        "{ \"username\":\"admin\", \"password\":\"b0fc-4801-4a30-8d39-2c01-6b6a\" }");

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    bearerToken = null;
    System.out.println("Logging out attempt resulted with HTTP code: " + response.statusCode());
  }
}
