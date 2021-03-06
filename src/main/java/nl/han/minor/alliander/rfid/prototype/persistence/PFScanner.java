package nl.han.minor.alliander.rfid.prototype.persistence;

import java.io.IOException;
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

import nl.han.minor.alliander.rfid.prototype.persistence.DAOs.TagDAO;
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
  public List<TagDAO> scanTags() {
    List<TagDAO> tags = new ArrayList<>();
    try {
      if (!setContinous) {
        setContinous();
        setContinous = true;
      }
      JSONArray rawTagData = readDataContinous();
      for (Object o : rawTagData) {
        if (o instanceof JSONObject) {
          if (((JSONObject) o).containsKey("UII")) {
            TagDAO tag = new TagDAO((String) ((JSONObject) o).get("UII"));
            if (((JSONObject) o).containsKey("data")) {
              tag.setData((String) ((JSONObject) o).get("data"));
            }
            if (((JSONObject) o).containsKey("RSSI")) {
              Long rssi = (long) ((JSONObject) o).get("RSSI");
              tag.setRSSI(rssi.intValue());
            }
            tags.add(tag);
            // tags.add(new BigInteger((String) ((JSONObject) o).get("UII"), 16));
          }
        }
      }
    } catch (Exception e) {
      if (org.json.simple.parser.ParseException.class.toString().equals(e.getClass().toString())) {
        bearerToken = null;
        System.out.println("Account deactivated as it was no longer avaiable");
      }
      System.err.println(e);
    }
    return tags;
  }

  @Override
  public boolean startScan() {
    try {
      setContinous();
      setContinous = true;
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
      // logout();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
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
    sendRequest(request);
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
    sendRequest(request);
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

  // /**
  // * Create a HttpRequest object for a post request
  // *
  // * @param url The relative URL (so without the base url) to the endpoint
  // * @param body
  // * @return HttpRequest
  // */
  // private HttpRequest createPostRequest(String url, String body) {
  // System.out.println("body:\n" + body);
  // return
  // HttpRequest.newBuilder().POST(BodyPublishers.ofString(body)).uri(URI.create(baseURL
  // + url))
  // .setHeader("User-Agent", "Java 11 HttpClient Bot").header("Authorization",
  // "Bearer " + getBearerToken())
  // .header("Content-Type", "application/json").build();
  // }

  /**
   * Create Put HttpRequest object for a put request
   * 
   * @param url  The relative URL (so without the base url) to the endpoint
   * @param body
   * @return HttpRequest
   */
  private HttpRequest createPutRequest(String url, String body) {
    System.out.println("body:\n" + body);
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

  // /**
  // * Log out from the system (I don't know if this even does anything as th\e
  // * bearer code still works after logout.)
  // *
  // * @throws Exception
  // */
  // private void logout() throws Exception {
  // HttpRequest request = createPostRequest("auth/logout",
  // "{ \"username\":\"admin\", \"password\":\"b0fc-4801-4a30-8d39-2c01-6b6a\"
  // }");
  // printRequest(request);
  // HttpResponse<String> response = httpClient.send(request,
  // HttpResponse.BodyHandlers.ofString());
  // bearerToken = null;
  // printRequestResult(response);
  // // System.out.println("Logging out attempt resulted with HTTP code: " +
  // // response.statusCode());
  // }

  // /**
  // * Prints the URI from the http request
  // *
  // * @param request
  // */
  // private void printRequest(HttpRequest request) {
  // // print URI
  // System.out.println("URI:\n " + request.uri());
  // // print Header
  // System.out.println("Headers:");
  // for (var entry : request.headers().map().entrySet()) {
  // System.out.println(entry.getKey() + " :: " + entry.getValue());
  // }
  // }

  // /**
  // * Prints the result of the request
  // *
  // * @param response
  // */
  // private void printRequestResult(HttpResponse<String> response) {
  // // print status code
  // System.out.println("HTTP response code:\n " + response.statusCode());
  // // print response body
  // System.out.println("Resonse body:\n " + (response.body().isEmpty() ? "empty"
  // : response.body()));
  // }
}
