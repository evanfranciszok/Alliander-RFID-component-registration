package nl.han.minor.alliander.rfid.prototype.persistence;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;

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
  private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
  private JSONParser parser = new JSONParser();

  /**
   * @return long[]
   */
  @Override
  public long[] scanTags() {

    // getBearerToken();
    try {
      identification();
      System.out.println("###########\nconfig");
      readConfig();
      System.out.println("###########\nData");
      readData();
      System.out.println("###########\nio");
      getIOconfig();
      System.out.println("###########\n\n");
    } catch (Exception e) {
      System.err.println(e);
    }

    return new long[] { 54321, 12345, 13579 };
  }

  /**
   * Log out from the system (I don't know if this does anything in the scanner.)
   * 
   * @throws Exception
   */
  private void logout() throws Exception {
    HttpRequest request = createPostRequest("auth/logout",
        "{ \"username\":\"admin\", \"password\":\"b0fc-4801-4a30-8d39-2c01-6b6a\" }");

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println("Logging out attempt resulted with HTTP code: " + response.statusCode());
  }

  /**
   * Returns the device identification
   * 
   * @throws Exception
   */
  private void identification() throws Exception {
    HttpRequest request = createGetRequest("identification");
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    // print status code
    System.out.println(parser.parse(response.body()));
  }

  /**
   * Get input and output configurations
   * 
   * @throws Exception
   */
  private void getIOconfig() throws Exception {
    HttpRequest request = createGetRequest("rfid/io");
    System.out.println(request.uri());
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.statusCode());
    // print status code
    System.out.println(response.body());

  }

  /**
   * Get the read transponder configuration
   * 
   * @throws Exception
   */
  private void readConfig() throws Exception {
    HttpRequest request = createGetRequest("rfid/transponder/read");
    System.out.println(request.uri());
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.statusCode());
    // print status code
    System.out.println(response.body());

  }

  /**
   * Return transponder data from continous read
   * 
   * @throws Exception
   */
  private void readData() throws Exception {
    HttpRequest request = createGetRequest("rfid/transponder/read/data");
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println(response.statusCode());
    // print status code
    System.out.println(response.body());

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
   * @return String
   */
  private String getBearerToken() {
    if (bearerToken != null) {
      return bearerToken;
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
    HttpRequest request = HttpRequest.newBuilder()
        .POST(BodyPublishers.ofString("{ \"username\":\"" + loginName + "\",\"password\":\"" + loginPassword + "\" }"))
        .uri(URI.create(baseURL + "auth/login")).setHeader("User-Agent", "Java 11 HttpClient Bot")
        .header("Content-Type", "application/json").build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println("inlog attempt HTTP code: " + response.statusCode());
    JSONObject json = (JSONObject) parser.parse(response.body());
    if (json.containsKey("token"))
      return json.get("token").toString();
    return null;
  }
}
