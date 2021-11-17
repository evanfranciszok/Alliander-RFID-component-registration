package nl.han.minor.alliander.rfid.prototype.persistence;

import java.net.URI;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;

import nl.han.minor.alliander.rfid.prototype.persistence.interfaces.IScanner;

//https://openjdk.java.net/groups/net/httpclient/intro.html

public class PFScanner implements IScanner {

  @Override
  public long[] scanTags() {

    getBearerToken();
    // HttpClient client = HttpClient.newHttpClient();
    // HttpRequest request =
    // HttpRequest.newBuilder().uri(URI.create("https://blockchain.info/ticker")).build();
    // client.sendAsync(request,
    // BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(System.out::println)
    // .join();

    return new long[] { 54321, 12345, 13579 };
  }

  private String bearerToken;

  private String getBearerToken() {
    if (bearerToken.isEmpty()) {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://blockchain.info/ticker")).build();
      client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(System.out::println)
          .join();
    }
    return null;
  }

}
