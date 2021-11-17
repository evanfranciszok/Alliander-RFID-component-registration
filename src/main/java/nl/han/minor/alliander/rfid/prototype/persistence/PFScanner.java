package nl.han.minor.alliander.rfid.prototype.persistence;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;

//https://openjdk.java.net/groups/net/httpclient/intro.html
//https://mkyong.com/java/how-to-send-http-request-getpost-in-java/

public class PFScanner implements IScanner {

  @Override
  public long[] scanTags() {

    getBearerToken();

    return new long[] { 54321, 12345, 13579 };
  }

  private String bearerToken;
  private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

  private String getBearerToken() {
    try {
      sendPost();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (bearerToken != null) {
      // ObjectMapper objectMapper = new ObjectMapper();
      // String requestBody = objectMapper
      // .writerWithDefaultPrettyPrinter()
      // .writeValueAsString(map);

      // HttpRequest request = HttpRequest.newBuilder(uri)
      // .header("Content-Type", "application/json")
      // .POST(BodyPublishers.ofString(requestBody))
      // .build();

      // return HttpClient.newHttpClient()
      // .sendAsync(request, BodyHandlers.ofString())
      // .thenApply(HttpResponse::statusCode)
      // .thenAccept(System.out::println);
    }
    return null;
  }

  private void sendGet() throws Exception {

    HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("https://httpbin.org/get"))
        .setHeader("User-Agent", "Java 11 HttpClient Bot").build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    // print status code
    System.out.println(response.statusCode());

    // print response body
    System.out.println(response.body());

  }

  private void sendPost() throws Exception {

    // form parameters
    Map<Object, Object> data = new HashMap<>();
    data.put("username", "admin");
    data.put("password", "b0fc-4801-4a30-8d39-2c01-6b6a");

    HttpRequest request = HttpRequest.newBuilder()
        .POST(BodyPublishers.ofString("{ \"username\":\"admin\", \"password\":\"b0fc-4801-4a30-8d39-2c01-6b6a\" }"))
        .uri(URI.create("http://192.168.1.10/api/auth/login")).setHeader("User-Agent", "Java 11 HttpClient Bot")
        .header("Content-Type", "application/json").build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    // print status code
    System.out.println(response.statusCode());

    // print response body
    System.out.println(response.body());

  }

  private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
    var builder = new StringBuilder();
    for (Map.Entry<Object, Object> entry : data.entrySet()) {
      if (builder.length() > 0) {
        builder.append("&");
      }
      builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
      builder.append("=");
      builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
    }
    System.out.println(builder.toString());
    return HttpRequest.BodyPublishers.ofString(builder.toString());
  }

}
