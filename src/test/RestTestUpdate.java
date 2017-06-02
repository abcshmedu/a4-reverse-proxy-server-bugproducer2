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
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_OK;
import static org.apache.http.HttpHeaders.USER_AGENT;
import static org.junit.Assert.assertEquals;

/**
 * Created by Tom on 02.06.2017.
 */
public class RestTestUpdate {


    private static final String NAME = "TestName1";
    private static final String NAME_ALT = "TestName2";
    private static final String TITLE = "TestTitle1";
    private static final String TITLE_ALT = "TestTitle2";
    private static final String ISBN = "3-446-193138";
    private static final String ISBN_ALT = "0-7475-51006";
    private static final String URL = "http://localhost:8082";
    private static final String EAN = "9783815820865";
    private static final String EAN_ALT = "9783827317100";

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
    public void testUpdateBook() throws IOException {
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
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        assertEquals(200, response.getStatusLine().getStatusCode());

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("title", TITLE_ALT);
        jsonObject2.put("author", NAME_ALT);
        //HttpPut httpPut = new HttpPut(URL_BOOKS + ISBN_ALT);
        HttpPut httpPut = TestUtils.getHttpPut(token, "/books/" + ISBN, 8084);
        httpPut.setEntity(new StringEntity(jsonObject2.toString()));
        httpPut.addHeader("content-Type", "application/json");
        HttpResponse response2 = client.execute(httpPut);
        System.out.println("Response Code : " + response2.getStatusLine().getStatusCode());
        assertEquals(200, response2.getStatusLine().getStatusCode());
    }

    @Test
    public void testUpdateDisc() throws IOException {
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
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        assertEquals(200, response.getStatusLine().getStatusCode());

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("fsk", 18);

        HttpPut httpPut = TestUtils.getHttpPut(token, "/discs/" + EAN, 8084);
        httpPut.setEntity(new StringEntity(jsonObject2.toString()));
        httpPut.addHeader("content-Type", "application/json");
        HttpResponse response2 = client.execute(httpPut);
        System.out.println("Response Code : " + response2.getStatusLine().getStatusCode());
        assertEquals(200, response2.getStatusLine().getStatusCode());

        TestUtils.getDiscs(token);


    }

    @Test
    public void testUpdateBookWrongISBN() throws IOException { //non existing ?
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
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        assertEquals(200, response.getStatusLine().getStatusCode());

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("title", TITLE_ALT);
        jsonObject2.put("author", NAME_ALT);
        //HttpPut httpPut = new HttpPut(URL_BOOKS + ISBN_ALT);
        HttpPut httpPut = TestUtils.getHttpPut(token, "/books/" + ISBN_ALT, 8084);
        httpPut.setEntity(new StringEntity(jsonObject2.toString()));
        httpPut.addHeader("content-Type", "application/json");
        HttpResponse response2 = client.execute(httpPut);
        System.out.println("Response Code : " + response2.getStatusLine().getStatusCode());
        assertEquals(400, response2.getStatusLine().getStatusCode());
    }

    @Test
    public void testUpdateDiscWrongEAN() throws IOException { //non existing ?
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
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        assertEquals(200, response.getStatusLine().getStatusCode());

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("fsk", 18);

        HttpPut httpPut = TestUtils.getHttpPut(token, "/discs/" + EAN_ALT, 8084);
        httpPut.setEntity(new StringEntity(jsonObject2.toString()));
        httpPut.addHeader("content-Type", "application/json");
        HttpResponse response2 = client.execute(httpPut);
        System.out.println("Response Code : " + response2.getStatusLine().getStatusCode());
        assertEquals(400, response2.getStatusLine().getStatusCode());

        TestUtils.getDiscs(token);


    }

}
