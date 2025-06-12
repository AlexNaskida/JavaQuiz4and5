package chatbot;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ApiClient {
    private static final HttpClient client = HttpClient.newHttpClient();
    private final String baseUrl;

    public ApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private String sendRequest(HttpRequest request) throws Exception {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int code = response.statusCode();
        if (code >= 200 && code < 300) {
            return response.body();
        } else {
            throw new RuntimeException("HTTP " + code + ": " + response.body());
        }
    }

    public String listPostsJson() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "?api=blogs"))
                .GET()
                .build();
        return sendRequest(req);
    }

    public String createPost(String title, String content, String author) throws Exception {
        String json = String.format(
                "{\"title\":\"%s\",\"content\":\"%s\",\"author\":\"%s\"}",
                title, content, author
        );
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "?api=blogs"))
                .header("Content-Type", "application/json; utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        return sendRequest(req);
    }

    public String getStatsJson() throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "?api=stats"))
                .GET()
                .build();
        return sendRequest(req);
    }
}
