import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;



public class TestUtils {
    public static HttpGet getHttpGet(String token, String path, int port) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("localhost")
                .setPort(port)
                .setPath("/shareit/media/" + token + path);

        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new HttpGet(uri);
    }

    public static HttpPut getHttpPut(String token, String path, int port) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("localhost")
                .setPort(port)
                .setPath("/shareit/media/" + token + path);

        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new HttpPut(uri);
    }

    public static HttpPost getHttpPost(String token, String path, int port) {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("localhost")
                .setPort(port)
                .setPath("/shareit/media/" + token + path);

        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return new HttpPost(uri);
    }

    public static HttpResponse getDiscs(String token) throws IOException {
        HttpClient client2 = HttpClientBuilder.create().build();
        HttpGet request = getHttpGet(token, "/discs", 8084);
        HttpResponse response2 = client2.execute(request);
        return response2;
    }

    public static void deleteAllLists() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http").setHost("localhost")
                .setPort(8080)
                .setPath("/shareit/media/reset");

        URI uri = null;
        try {
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        client.execute(new HttpGet(uri));
    }

}
