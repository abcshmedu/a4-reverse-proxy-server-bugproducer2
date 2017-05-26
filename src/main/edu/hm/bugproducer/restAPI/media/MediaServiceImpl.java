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


            Jwt jwt = Jwts.parser()
                    .setSigningKey("secret".getBytes("UTF-8"))
                    .parseClaimsJws(jwtString);



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
    public MediaServiceResult addDisc(Disc disc) {
        MediaServiceResult mediaServiceResult = MSR_INTERNAL_SERVER_ERROR;
        System.out.println(disc.getFsk());

        if (disc == null) {
            mediaServiceResult = MSR_NO_CONTENT;
        } else if (!EAN13CheckDigit.EAN13_CHECK_DIGIT.isValid(disc.getBarcode())) {
            mediaServiceResult = MSR_BAD_REQUEST;
        } else if (disc.getBarcode().isEmpty() || disc.getDirector().isEmpty() || disc.getTitle().isEmpty() || disc.getFsk() < 0) {
            mediaServiceResult = MSR_NO_CONTENT;
        } else {
            if (discs.isEmpty()) {
                mediaServiceResult = MSR_OK;
                discs.add(disc);
            } else {

                ListIterator<Disc> lir = discs.listIterator();

                while (lir.hasNext()) {
                    if (lir.next().getBarcode().equals(disc.getBarcode())) {
                        mediaServiceResult = MSR_BAD_REQUEST;
                    } else {
                        lir.add(disc);
                        mediaServiceResult = MSR_OK;
                    }
                }
            }

        }
        return mediaServiceResult;
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

    //todo Was passiert bei ungültiger ISBN
    @Override
    public MediaServiceResult updateBook(String isbn, Book newBook) {
        MediaServiceResult mediaServiceResult = MSR_INTERNAL_SERVER_ERROR;

        return mediaServiceResult;
    }

    @Override
    public MediaServiceResult updateDisc(String barcode, Disc newDisc) {
        MediaServiceResult mediaServiceResult = MSR_INTERNAL_SERVER_ERROR;

        return mediaServiceResult;
    }


}
