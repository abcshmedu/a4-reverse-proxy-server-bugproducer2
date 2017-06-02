import edu.hm.bugproducer.JettyStarter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpHeaders.USER_AGENT;
import static org.junit.Assert.assertEquals;

import static edu.hm.bugproducer.restAPI.MediaServiceResult.*;

@SuppressWarnings("Duplicates")
public class RestTestCreate {

    private static final String NAME = "TestName1";
    private static final String TITLE = "TestTitle1";
    private static final String ISBN = "3-446-193138";
    private static final String URL = "http://localhost:8082";
    private static final String EAN = "9783815820865";

    private static final String WRONG_ISBN = "0-7475006";
    private static final String WRONG_EAN = "3-446-19313";

    //URL
    private static final String URLLOGIN = "http://localhost:8082/auth/login/";

    //User
    private static final String USER = "John Doe";
    private static final String PASSWORD = "geheim";

    private JettyStarter jettyStarter;

    @Before
    public void openConnection() throws Exception {
        jettyStarter = new JettyStarter();
        jettyStarter.startJetty();

        TestUtils.deleteAllLists();
    }

    @After
    public void closeConnection() throws Exception {
        jettyStarter.stopJetty();
    }

    @Test
    public void testConnection() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(URL);

        // add request header
        request.addHeader("User-Agent", USER_AGENT);
        HttpResponse response = client.execute(request);

        System.out.println("Response Code : "
                + response.getStatusLine().getStatusCode());
    }

    private HttpResponse login() throws IOException {
        List<NameValuePair> userData = new ArrayList<>();

        userData.add(new BasicNameValuePair("user", USER));
        userData.add(new BasicNameValuePair("password", PASSWORD));


        HttpClient client = HttpClientBuilder.create().build();
        HttpPost loginToWebsite = new HttpPost(URLLOGIN);

        loginToWebsite.setEntity(new UrlEncodedFormEntity(userData));
        loginToWebsite.addHeader("content-Type", "application/x-www-form-urlencoded");
        return client.execute(loginToWebsite);
    }

    @Test
    public void testCreateBook() throws IOException {
        JSONObject book = new JSONObject();
        book.put("title", TITLE);
        book.put("author", NAME);
        book.put("isbn", ISBN);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        HttpResponse loginResponse = login();
        assertEquals(MSR_OK.getCode(), loginResponse.getStatusLine().getStatusCode());

        String token = IOUtils.toString(loginResponse.getEntity().getContent());
        HttpPost httpPost = TestUtils.getHttpPost(token, "/books", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(book.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity()));
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testCreateDisc() throws IOException {
        JSONObject disc = new JSONObject();
        disc.put("title", TITLE);
        disc.put("barcode", EAN);
        disc.put("director", NAME);
        disc.put("fsk", 16);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        HttpResponse loginResponse = login();
        assertEquals(MSR_OK.getCode(), loginResponse.getStatusLine().getStatusCode());

        String token = IOUtils.toString(loginResponse.getEntity().getContent());
        HttpPost httpPost = TestUtils.getHttpPost(token, "/discs", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(disc.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity()));
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testCreateDiscEmpty() throws IOException {
        JSONObject disc = new JSONObject();
        disc.put("title", "");
        disc.put("barcode", "");
        disc.put("director", "");
        disc.put("fsk", 0);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        HttpResponse loginResponse = login();
        assertEquals(MSR_OK.getCode(), loginResponse.getStatusLine().getStatusCode());

        String token = IOUtils.toString(loginResponse.getEntity().getContent());
        HttpPost httpPost = TestUtils.getHttpPost(token, "/discs", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(disc.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity()));
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testCreateDiscWrongEAN() throws IOException {
        JSONObject disc = new JSONObject();
        disc.put("title", TITLE);
        disc.put("barcode", WRONG_EAN);
        disc.put("director", NAME);
        disc.put("fsk", 16);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        HttpResponse loginResponse = login();
        assertEquals(MSR_OK.getCode(), loginResponse.getStatusLine().getStatusCode());

        String token = IOUtils.toString(loginResponse.getEntity().getContent());
        HttpPost httpPost = TestUtils.getHttpPost(token, "/discs", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(disc.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity()));
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testCreateBookWrongIsbn() throws IOException {
        JSONObject book = new JSONObject();
        book.put("title", TITLE);
        book.put("author", NAME);
        book.put("isbn", WRONG_ISBN);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        HttpResponse loginResponse = login();
        assertEquals(MSR_OK.getCode(), loginResponse.getStatusLine().getStatusCode());

        String token = IOUtils.toString(loginResponse.getEntity().getContent());
        HttpPost httpPost = TestUtils.getHttpPost(token, "/books", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(book.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity()));
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testCreateBookDuplicate() throws IOException {
        JSONObject book = new JSONObject();
        book.put("title", TITLE);
        book.put("author", NAME);
        book.put("isbn", ISBN);

        HttpClient client = HttpClientBuilder.create().build();
        // LOGIN
        HttpResponse loginResponse = login();
        assertEquals(MSR_OK.getCode(), loginResponse.getStatusLine().getStatusCode());

        String token = IOUtils.toString(loginResponse.getEntity().getContent());
        HttpPost httpPost = TestUtils.getHttpPost(token, "/books", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(book.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity()));
        assertEquals(200, response.getStatusLine().getStatusCode());
        HttpResponse response2 = client.execute(httpPost);
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity()));
        assertEquals(400, response2.getStatusLine().getStatusCode());
    }

    @Test
    public void testCreateDiscDuplicate() throws IOException {
        JSONObject disc = new JSONObject();
        disc.put("title", TITLE);
        disc.put("barcode", EAN);
        disc.put("director", NAME);
        disc.put("fsk", 16);

        JSONObject disc2 = new JSONObject();
        disc2.put("title", TITLE);
        disc2.put("barcode", EAN);
        disc2.put("director", NAME);
        disc2.put("fsk", 16);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        HttpResponse loginResponse = login();
        assertEquals(MSR_OK.getCode(), loginResponse.getStatusLine().getStatusCode());
        String token = IOUtils.toString(loginResponse.getEntity().getContent());

        HttpPost addFirstDisc = TestUtils.getHttpPost(token, "/discs", 8084);
        addFirstDisc.setEntity(new StringEntity(disc.toString()));
        addFirstDisc.addHeader("content-Type", "application/json");


        HttpPost addSecondDisc = TestUtils.getHttpPost(token, "/discs", 8084);
        addSecondDisc.setEntity(new StringEntity(disc.toString()));
        addSecondDisc.addHeader("content-Type", "application/json");

        HttpResponse response = client.execute(addFirstDisc);
        HttpResponse response2 = client.execute(addSecondDisc);

        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity()));
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity()));
        assertEquals(400, response2.getStatusLine().getStatusCode());
    }

    @Test
    public void testCreateBookEmpty() throws IOException {
        JSONObject book = new JSONObject();
        book.put("title", "");
        book.put("author", "");
        book.put("isbn", "");

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        HttpResponse loginResponse = login();
        assertEquals(MSR_OK.getCode(), loginResponse.getStatusLine().getStatusCode());

        String token = IOUtils.toString(loginResponse.getEntity().getContent());
        HttpPost httpPost = TestUtils.getHttpPost(token, "/books", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(book.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity()));
        assertEquals(400, response.getStatusLine().getStatusCode());
    }


}