import edu.hm.bugproducer.JettyStarter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpHeaders.USER_AGENT;
import static org.junit.Assert.assertEquals;

import static edu.hm.bugproducer.restAPI.MediaServiceResult.*;

@SuppressWarnings("Duplicates")
public class RestTest {

    private static final String USER_NAME = "Joh";
    private static final String NAME = "TestName1";
    private static final String NAME_ALT = "TestName2";
    private static final String TITLE = "TestTitle1";
    private static final String TITLE_ALT = "TestTitle2";
    private static final String ISBN = "3-446-193138";
    private static final String ISBN_ALT = "0-7475-51006";
    private static final String URL = "http://localhost:8082";
    private static final String EAN = "9783815820865";
    private static final String EAN_ALT = "9783827317100";
    private static final String URL_BOOKS_80 = "http://localhost:8080/shareit/media/books/";
    private static final String URL_BOOKS_84 = "http://localhost:8084/shareit/media/books/";
    private static final String URL_DISCS = "http://localhost:8080/shareit/media/discs/";
    private static final String URL_COPIES_BOOKS = "http://localhost:8080/shareit/copy/books";
    private static final String URL_COPIES_DISCS = "http://localhost:8080/shareit/copy/discs";
    private static final String URL_COPIES = "http://localhost:8080/shareit/copy/";
    private static final String URL_BOOK_COPY_ONE = URL_COPIES_BOOKS + "/" + ISBN + "/" + 1;
    private static final String URL_DISC_COPY_ONE = URL_COPIES_DISCS + "/" + EAN + "/" + 1;

    //URL
    private static final String URLLOGIN = "http://localhost:8082/auth/login/";
    private static final String URLVERIFY = "http://localhost:8082/auth/verify/";

    //User
    private static final String USER = "John Doe";
    private static final String PASSWORD = "geheim";

    private static final String CORRUPTUSER = "Jane Doe";
    private static final String CORRUPTPASSWORD = "offen";


    private JettyStarter jettyStarter;

    @Before
    public void openConnection() throws Exception {
        jettyStarter = new JettyStarter();
        jettyStarter.startJetty();

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
    public void testGetBooks() throws IOException {
        JSONObject book = new JSONObject();
        book.put("title", TITLE);
        book.put("author", NAME);
        book.put("isbn", ISBN);

        JSONObject book2 = new JSONObject();
        book2.put("title", TITLE);
        book2.put("author", NAME);
        book2.put("isbn", ISBN_ALT);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        HttpResponse loginResponse = login();

        assertEquals(MSR_OK.getCode(), loginResponse.getStatusLine().getStatusCode());

        String token = IOUtils.toString(loginResponse.getEntity().getContent());

        HttpPost addFirstBook = new HttpPost(URL_BOOKS_80);
        HttpPost addSecondBook = new HttpPost(URL_BOOKS_80);

        addFirstBook.setEntity(new StringEntity(book.toString()));
        addSecondBook.setEntity(new StringEntity(book2.toString()));

        addFirstBook.addHeader("content-Type", "application/json");
        addSecondBook.addHeader("content-Type", "application/json");

        HttpResponse response = client.execute(addFirstBook);
        assertEquals(200, response.getStatusLine().getStatusCode());

        response = client.execute(addSecondBook);
        assertEquals(200, response.getStatusLine().getStatusCode());

        HttpGet request = new HttpGet(URL_BOOKS_84 + token);
        HttpResponse response2 = client.execute(request);
        System.out.println("Ergebnis:");
        System.out.println(EntityUtils.toString(response2.getEntity()));
        assertEquals(200, response2.getStatusLine().getStatusCode());
    }

}