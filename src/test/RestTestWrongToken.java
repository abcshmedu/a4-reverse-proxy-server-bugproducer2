import edu.hm.bugproducer.JettyStarter;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("Duplicates")
public class RestTestWrongToken {
    private static final String NAME = "TestName1";
    private static final String NAME_ALT = "TestName2";
    private static final String TITLE = "TestTitle1";
    private static final String TITLE_ALT = "TestTitle2";
    private static final String ISBN = "3-446-193138";
    private static final String ISBN_ALT = "0-7475-51006";
    private static final String EAN = "9783815820865";
    private static final String EAN_ALT = "9783827317100";


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

        TestUtils.deleteAllLists();
    }


    @After
    public void closeConnection() throws Exception {
        jettyStarter.stopJetty();
    }


    @Test
    public void testGetBooksEmptyWrongToken() throws IOException {
        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";


        HttpGet request = TestUtils.getHttpGet(token, "/books", 8084);
        HttpResponse response = client.execute(request);
        assertEquals(401, response.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void testGetDiscsEmptyWrongToken() throws IOException {

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";

        HttpResponse response = TestUtils.getDiscs(token);
        assertEquals(401, response.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void testCreateBooksWrongToken() throws IOException {
        JSONObject book = new JSONObject();
        book.put("title", TITLE);
        book.put("author", NAME);
        book.put("isbn", ISBN);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";

        HttpPost httpPost = TestUtils.getHttpPost(token, "/books", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(book.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        assertEquals(401, response.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity())); //nicht so wie wir haben wollen
    }
    @Test
    public void testCreateDiscWrongToken() throws IOException {
        JSONObject disc = new JSONObject();
        disc.put("title", TITLE);
        disc.put("barcode", EAN);
        disc.put("director", NAME);
        disc.put("fsk", 16);

        HttpClient client = HttpClientBuilder.create().build();


        String token = "wqwertzuioosdfghj";

        HttpPost httpPost = TestUtils.getHttpPost(token, "/discs", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(disc.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        assertEquals(401, response.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response.getEntity())); //???
    }

    @Test
    public void testUpdateBookWrongToken() throws IOException {
        JSONObject book = new JSONObject();
        book.put("title", TITLE);
        book.put("author", NAME);
        book.put("isbn", ISBN);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";

        HttpPost httpPost = TestUtils.getHttpPost(token, "/books", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(book.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        assertEquals(401, response.getStatusLine().getStatusCode());

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("title", TITLE_ALT);
        jsonObject2.put("author", NAME_ALT);
        //HttpPut httpPut = new HttpPut(URL_BOOKS + ISBN_ALT);
        HttpPut httpPut = TestUtils.getHttpPut(token, "/books/" + ISBN, 8084);
        httpPut.setEntity(new StringEntity(jsonObject2.toString()));
        httpPut.addHeader("content-Type", "application/json");
        HttpResponse response2 = client.execute(httpPut);
        System.out.println("Response Code : " + response2.getStatusLine().getStatusCode());
        assertEquals(401, response2.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity())); //empty ?
    }

    @Test
    public void testUpdateDiscWrongToken() throws IOException {
        JSONObject disc = new JSONObject();
        disc.put("title", TITLE);
        disc.put("barcode", EAN);
        disc.put("director", NAME);
        disc.put("fsk", 16);


        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";

        HttpPost httpPost = TestUtils.getHttpPost(token, "/discs", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(disc.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        assertEquals(401, response.getStatusLine().getStatusCode());

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("fsk", 18);

        HttpPut httpPut = TestUtils.getHttpPut(token, "/discs/" + ISBN, 8084);
        httpPut.setEntity(new StringEntity(jsonObject2.toString()));
        httpPut.addHeader("content-Type", "application/json");
        HttpResponse response2 = client.execute(httpPut);
        System.out.println("Response Code : " + response2.getStatusLine().getStatusCode());
        assertEquals(401, response2.getStatusLine().getStatusCode());
        TestUtils.getDiscs(token);
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity())); //Empty??


    }

    @Test
    public void testUpdateBookWrongISBNWrongToken() throws IOException {
        JSONObject book = new JSONObject();
        book.put("title", TITLE);
        book.put("author", NAME);
        book.put("isbn", ISBN);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";

        HttpPost httpPost = TestUtils.getHttpPost(token, "/books", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(book.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        assertEquals(401, response.getStatusLine().getStatusCode());

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("title", TITLE_ALT);
        jsonObject2.put("author", NAME_ALT);
        //HttpPut httpPut = new HttpPut(URL_BOOKS + ISBN_ALT);
        HttpPut httpPut = TestUtils.getHttpPut(token, "/books/" + ISBN_ALT, 8084);
        httpPut.setEntity(new StringEntity(jsonObject2.toString()));
        httpPut.addHeader("content-Type", "application/json");
        HttpResponse response2 = client.execute(httpPut);
        System.out.println("Response Code : " + response2.getStatusLine().getStatusCode());
        assertEquals(401, response2.getStatusLine().getStatusCode());
        //Empty???
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity()));
    }

    @Test
    public void testUpdateDiscWrongEANWrongToken() throws IOException {
        JSONObject disc = new JSONObject();
        disc.put("title", TITLE);
        disc.put("barcode", EAN);
        disc.put("director", NAME);
        disc.put("fsk", 16);


        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";

        HttpPost httpPost = TestUtils.getHttpPost(token, "/discs", 8084);
        httpPost.addHeader("content-Type", "application/json");
        httpPost.setEntity(new StringEntity(disc.toString()));

        HttpResponse response = client.execute(httpPost);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        assertEquals(401, response.getStatusLine().getStatusCode());

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("fsk", 18);

        HttpPut httpPut = TestUtils.getHttpPut(token, "/discs/" + EAN_ALT, 8084);
        httpPut.setEntity(new StringEntity(jsonObject2.toString()));
        httpPut.addHeader("content-Type", "application/json");
        HttpResponse response2 = client.execute(httpPut);
        System.out.println("Response Code : " + response2.getStatusLine().getStatusCode());
        assertEquals(401, response2.getStatusLine().getStatusCode());
        TestUtils.getDiscs(token);
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity())); //Empty??


    }

    @Test
    public void testGetBookWrongToken() throws IOException {
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
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";


        HttpPost addFirstBook = TestUtils.getHttpPost(token, "/books", 8084);
        HttpPost addSecondBook = TestUtils.getHttpPost(token, "/books", 8084);

        addFirstBook.setEntity(new StringEntity(book.toString()));
        addSecondBook.setEntity(new StringEntity(book2.toString()));

        addFirstBook.addHeader("content-Type", "application/json");
        addSecondBook.addHeader("content-Type", "application/json");

        HttpResponse response = client.execute(addFirstBook);
        assertEquals(401, response.getStatusLine().getStatusCode());

        response = client.execute(addSecondBook);
        assertEquals(401, response.getStatusLine().getStatusCode());

        HttpGet request = TestUtils.getHttpGet(token, "/books/" + ISBN, 8084);
        HttpClient client2 = HttpClientBuilder.create().build();
        HttpResponse response2 = client2.execute(request);
        System.out.println("Ergebnis:");
        assertEquals(401, response2.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity()));
    }

    @Test
    public void testGetBookWrongISBNWrongToken() throws IOException {
        JSONObject book = new JSONObject();
        book.put("title", TITLE);
        book.put("author", NAME);
        book.put("isbn", ISBN);
        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";


        HttpPost addFirstBook = TestUtils.getHttpPost(token, "/books", 8084);

        addFirstBook.setEntity(new StringEntity(book.toString()));

        addFirstBook.addHeader("content-Type", "application/json");

        HttpResponse response = client.execute(addFirstBook);
        assertEquals(401, response.getStatusLine().getStatusCode());

        HttpGet request = TestUtils.getHttpGet(token, "/books/" + ISBN_ALT, 8084);
        HttpClient client2 = HttpClientBuilder.create().build();
        HttpResponse response2 = client2.execute(request);
        System.out.println("Ergebnis:");
        assertEquals(401, response2.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity()));
    }

    @Test
    public void testGetDiscWrongToken() throws IOException {
        JSONObject disc = new JSONObject();
        disc.put("title", TITLE);
        disc.put("barcode", EAN);
        disc.put("director", NAME);
        disc.put("fsk", 16);

        HttpClient client = HttpClientBuilder.create().build();

        // LOGIN
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";


        HttpPost addFirstBook = TestUtils.getHttpPost(token, "/discs", 8084);

        addFirstBook.setEntity(new StringEntity(disc.toString()));
        addFirstBook.addHeader("content-Type", "application/json");

        HttpResponse response = client.execute(addFirstBook);
        assertEquals(401, response.getStatusLine().getStatusCode());

        HttpGet request = TestUtils.getHttpGet(token, "/discs/" + EAN, 8084);
        HttpResponse response2 = client.execute(request);
        assertEquals(401, response2.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity()));
    }

    @Test
    public void testGetDiscWrongEANWrongToken() throws IOException {
        JSONObject disc = new JSONObject();
        disc.put("title", TITLE);
        disc.put("barcode", EAN);
        disc.put("director", NAME);
        disc.put("fsk", 16);

        HttpClient client = HttpClientBuilder.create().build();
        // LOGIN
        //HttpResponse loginResponse = loginWrong();
        String token = "wqwertzuioosdfghj";


        HttpPost addFirstBook = TestUtils.getHttpPost(token, "/discs", 8084);

        addFirstBook.setEntity(new StringEntity(disc.toString()));
        addFirstBook.addHeader("content-Type", "application/json");

        HttpResponse response = client.execute(addFirstBook);
        assertEquals(401, response.getStatusLine().getStatusCode());

        HttpGet request = TestUtils.getHttpGet(token, "/discs/" + EAN_ALT, 8084);
        HttpResponse response2 = client.execute(request);
        assertEquals(401, response2.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity()));
    }

    @Test
    public void testGetBooksWrongToken() throws IOException {
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
        //HttpResponse loginResponse = loginWrong();

        String token = "wqwertzuioosdfghj";

        HttpPost addFirstBook = TestUtils.getHttpPost(token, "/books", 8084);
        HttpPost addSecondBook = TestUtils.getHttpPost(token, "/books", 8084);

        addFirstBook.setEntity(new StringEntity(book.toString()));
        addSecondBook.setEntity(new StringEntity(book2.toString()));

        addFirstBook.addHeader("content-Type", "application/json");
        addSecondBook.addHeader("content-Type", "application/json");

        HttpResponse response = client.execute(addFirstBook);
        assertEquals(401, response.getStatusLine().getStatusCode());

        response = client.execute(addSecondBook);
        assertEquals(401, response.getStatusLine().getStatusCode());

        HttpGet request = TestUtils.getHttpGet(token, "/books", 8084);
        HttpClient client2 = HttpClientBuilder.create().build();
        HttpResponse response2 = client2.execute(request);

        assertEquals(401, response2.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity()));

    }

    @Test
    public void testGetDiscsWrongToken() throws IOException {
        JSONObject disc = new JSONObject();
        disc.put("title", TITLE);
        disc.put("barcode", EAN);
        disc.put("director", NAME);
        disc.put("fsk", 16);

        JSONObject disc2 = new JSONObject();
        disc2.put("title", TITLE_ALT);
        disc2.put("barcode", EAN_ALT);
        disc2.put("director", NAME_ALT);
        disc2.put("fsk", 18);

        HttpClient client = HttpClientBuilder.create().build();


        String token = "wqwertzuioosdfghj";

        HttpPost addFirstDisc = TestUtils.getHttpPost(token, "/discs", 8084);
        HttpPost addSecondDisc = TestUtils.getHttpPost(token, "/discs", 8084);

        addFirstDisc.setEntity(new StringEntity(disc.toString()));
        addSecondDisc.setEntity(new StringEntity(disc2.toString()));

        addFirstDisc.addHeader("content-Type", "application/json");
        addSecondDisc.addHeader("content-Type", "application/json");

        HttpResponse response = client.execute(addFirstDisc);
        assertEquals(401, response.getStatusLine().getStatusCode());

        response = client.execute(addSecondDisc);
        assertEquals(401, response.getStatusLine().getStatusCode());

        HttpResponse response2 = TestUtils.getDiscs(token);
        assertEquals(401, response2.getStatusLine().getStatusCode());
        System.out.print("Ergebnis: "+EntityUtils.toString(response2.getEntity()));

    }



}
