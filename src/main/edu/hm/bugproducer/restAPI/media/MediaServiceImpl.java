package edu.hm.bugproducer.restAPI.media;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import edu.hm.bugproducer.Utils.Isbn;
import edu.hm.bugproducer.models.Book;
import edu.hm.bugproducer.models.Disc;
import edu.hm.bugproducer.restAPI.MediaServiceResult;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javafx.util.Pair;
import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static edu.hm.bugproducer.restAPI.MediaServiceResult.*;
import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_NOT_FOUND;
import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_OK;

@SuppressWarnings("Duplicates")
public class MediaServiceImpl implements MediaService {


    private static final String URLVERIFY = "http://localhost:8082/auth/verify/";
    private static final String URL_BOOKS = "http://localhost:8080/shareit/media/books/";
    private static final String URL_DISCS = "http://localhost:8080/shareit/media/discs/";

    public static List<Book> books = new ArrayList<>();
    public static List<Disc> discs = new ArrayList<>();

    @Override
    public HttpResponse addBook(String token, Book book) throws IOException {
        System.out.println("addBooks: start");
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet verify = new HttpGet(URLVERIFY + token);
        HttpResponse authResponse = client.execute(verify);

        if (authResponse.getStatusLine().getStatusCode() == MSR_OK.getCode()) {

            String jwtString = IOUtils.toString(authResponse.getEntity().getContent());

           String user = Jwts.parser()
                    .setSigningKey("secret".getBytes("UTF-8"))
                    .parseClaimsJws(jwtString).getBody().getSubject();

            Map<String, Object> headerClaims = new HashMap();
            headerClaims.put("type", Header.JWT_TYPE);
            String compactJws = null;

            ObjectWriter jbook = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = jbook.writeValueAsString(book);

            try {
                compactJws = Jwts.builder()
                        .setSubject(user)
                        .claim("book",json)
                        .setHeader(headerClaims)
                        .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                        .compact();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            HttpPost addBook = new HttpPost(URL_BOOKS);

            addBook.setEntity(new StringEntity(compactJws));
            addBook.addHeader("content-Type", "application/json");
            HttpResponse createResponse =client.execute(addBook);

            System.out.println(createResponse.getStatusLine().getStatusCode());

            return createResponse;

        }

        return authResponse;


    }




    @Override
    public HttpResponse addDisc(String token, Disc disc) throws IOException {
        System.out.println("addDisc: start");
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet verify = new HttpGet(URLVERIFY + token);
        HttpResponse authResponse = client.execute(verify);

        if (authResponse.getStatusLine().getStatusCode() == MSR_OK.getCode()) {

            String jwtString = IOUtils.toString(authResponse.getEntity().getContent());

            String user = Jwts.parser()
                    .setSigningKey("secret".getBytes("UTF-8"))
                    .parseClaimsJws(jwtString).getBody().getSubject();

            Map<String, Object> headerClaims = new HashMap();
            headerClaims.put("type", Header.JWT_TYPE);
            String compactJws = null;

            ObjectWriter jdisc = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = jdisc.writeValueAsString(disc);

            try {
                compactJws = Jwts.builder()
                        .setSubject(user)
                        .claim("disc",json)
                        .setHeader(headerClaims)
                        .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                        .compact();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            HttpPost addDisc = new HttpPost(URL_DISCS);

            addDisc.setEntity(new StringEntity(compactJws));
            addDisc.addHeader("content-Type", "application/json");
            HttpResponse createResponse =client.execute(addDisc);

            System.out.println(createResponse.getStatusLine().getStatusCode());

            return createResponse;

        }

        return authResponse;
    }


    @Override
    public HttpEntity getBooks(String token) throws IOException {
        System.out.println("getBooks: start");
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet verify = new HttpGet(URLVERIFY + token);
        HttpResponse authResponse = client.execute(verify);

        if (authResponse.getStatusLine().getStatusCode() == MSR_OK.getCode()) {
            HttpGet request = new HttpGet(URL_BOOKS);
            HttpResponse shareItResponse = client.execute(request);
                //toDo change entity to Response
            if (shareItResponse.getStatusLine().getStatusCode() == MSR_OK.getCode()) {
                return shareItResponse.getEntity();
            }
        }
        System.out.println("getBooks: end");
        return authResponse.getEntity();
    }

    @Override
    public HttpEntity getDiscs(String token) throws IOException {
        System.out.println("getDiscs: start");
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet verify = new HttpGet(URLVERIFY + token);
        HttpResponse authResponse = client.execute(verify);

        if (authResponse.getStatusLine().getStatusCode() == MSR_OK.getCode()) {
            HttpGet request = new HttpGet(URL_DISCS);
            HttpResponse shareItResponse = client.execute(request);
            if (shareItResponse.getStatusLine().getStatusCode() == MSR_OK.getCode()) {
                return shareItResponse.getEntity();
            }
        }
        System.out.println("getDiscs: end");
        return authResponse.getEntity();
    }

    // ------------------
    //toDo alles ab hier!

    @Override
    public Pair<MediaServiceResult, Book> getBook(String isbn) {
        Pair<MediaServiceResult, Book> myResult = null;

        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                myResult = new Pair<>(MSR_OK, b);
            } else {
                myResult = new Pair<>(MSR_NOT_FOUND, null);
            }
        }
        return myResult;
    }

    @Override
    public Pair<MediaServiceResult, Disc> getDisc(String barcode) {
        Pair<MediaServiceResult, Disc> myResult = null;

        for (Disc d : discs) {
            if (d.getBarcode().equals(barcode)) {
                myResult = new Pair<>(MSR_OK, d);
            } else {
                myResult = new Pair<>(MSR_NOT_FOUND, null);
            }
        }
        return myResult;
    }


    @Override
    public HttpResponse updateBook(String token, String isbn, Book newBook) throws IOException {
        MediaServiceResult mediaServiceResult = MSR_INTERNAL_SERVER_ERROR;

        System.out.println("updateBook: start");
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet verify = new HttpGet(URLVERIFY + token);
        HttpResponse authResponse = client.execute(verify);

        if (authResponse.getStatusLine().getStatusCode() == MSR_OK.getCode()) {

            String jwtString = IOUtils.toString(authResponse.getEntity().getContent());

            String user = Jwts.parser()
                    .setSigningKey("secret".getBytes("UTF-8"))
                    .parseClaimsJws(jwtString).getBody().getSubject();

            Map<String, Object> headerClaims = new HashMap();
            headerClaims.put("type", Header.JWT_TYPE);
            String compactJws = null;

            ObjectWriter jbook = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = jbook.writeValueAsString(newBook);

            try {
                compactJws = Jwts.builder()
                        .setSubject(user)
                        .claim("book",json)
                        .setHeader(headerClaims)
                        .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                        .compact();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            HttpPut updateBook = new HttpPut(URL_BOOKS+isbn);
            updateBook.setEntity(new StringEntity(compactJws));
            updateBook.addHeader("content-Type", "application/json");
            HttpResponse shareItResponse =client.execute(updateBook);
            System.out.println(shareItResponse.getStatusLine().getStatusCode());
            return shareItResponse;

        }
        System.out.println("update: end");
        return authResponse;
    }



    @Override
    public HttpResponse updateDisc(String token, String barcode, Disc newDisc) throws IOException {
        MediaServiceResult mediaServiceResult = MSR_INTERNAL_SERVER_ERROR;

        System.out.println("updateBook: start");
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet verify = new HttpGet(URLVERIFY + token);
        HttpResponse authResponse = client.execute(verify);

        if (authResponse.getStatusLine().getStatusCode() == MSR_OK.getCode()) {

            String jwtString = IOUtils.toString(authResponse.getEntity().getContent());

            String user = Jwts.parser()
                    .setSigningKey("secret".getBytes("UTF-8"))
                    .parseClaimsJws(jwtString).getBody().getSubject();

            Map<String, Object> headerClaims = new HashMap();
            headerClaims.put("type", Header.JWT_TYPE);
            String compactJws = null;

            ObjectWriter jdisc = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = jdisc.writeValueAsString(newDisc);

            try {
                compactJws = Jwts.builder()
                        .setSubject(user)
                        .claim("disc",json)
                        .setHeader(headerClaims)
                        .signWith(SignatureAlgorithm.HS256, "secret".getBytes("UTF-8"))
                        .compact();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            HttpPut updateDisc = new HttpPut(URL_DISCS+barcode);
            updateDisc.setEntity(new StringEntity(compactJws));
            updateDisc.addHeader("content-Type", "application/json");
            HttpResponse updateResponse =client.execute(updateDisc);
            System.out.println(updateResponse.getStatusLine().getStatusCode());

            return updateResponse;

        }

        return authResponse;
    }


}
