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

  /**
   * @return long[]
   */
  @Override
  public long[] scanTags() {

    // getBearerToken();
    try {
      identification();
    } catch (Exception e) {
      System.err.println(e);
    }

    return new long[] { 54321, 12345, 13579 };
  }

  private String bearerToken = null;
  private String baseURL = "http://192.168.1.10/api/";
  private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
  private JSONParser parser = new JSONParser();

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
   * @throws Exception
   */
  private void identification() throws Exception {
    HttpRequest request = createGetRequest("identification");
    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    // print status code
    System.out.println(parser.parse(response.body()));
  }

  /**
   * @return String
   * @throws Exception
   */
  private String login() throws Exception {
    HttpRequest request = HttpRequest.newBuilder()
        .POST(BodyPublishers.ofString("{ \"username\":\"admin\",\"password\":\"b0fc-4801-4a30-8d39-2c01-6b6a\" }"))
        .uri(URI.create(baseURL + "auth/login")).setHeader("User-Agent", "Java 11 HttpClient Bot")
        .header("Content-Type", "application/json").build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    System.out.println("inlog attempt HTTP code: " + response.statusCode());
    JSONObject json = (JSONObject) parser.parse(response.body());
    if (json.containsKey("token"))
      return json.get("token").toString();
    return null;
  }

  /**
   * Logging out of the P+F scanner. I don't know if this does anything in the
   * scanner.
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
   * @param url
   * @return HttpRequest
   */
  private HttpRequest createGetRequest(String url) {
    return HttpRequest.newBuilder().GET().uri(URI.create(baseURL + url))
        .setHeader("User-Agent", "Java 11 HttpClient Bot").header("Authorization", "Bearer " + getBearerToken())
        .build();
  }

  /**
   * @param url
   * @param body
   * @return HttpRequest
   */
  private HttpRequest createPostRequest(String url, String body) {
    return HttpRequest.newBuilder().POST(BodyPublishers.ofString(body)).uri(URI.create(baseURL + url))
        .setHeader("User-Agent", "Java 11 HttpClient Bot").header("Authorization", "Bearer " + getBearerToken())
        .header("Content-Type", "application/json").build();
  }
}
