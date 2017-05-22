package edu.hm.bugproducer.restAPI.media;

import edu.hm.bugproducer.Utils.Isbn;
import edu.hm.bugproducer.models.Book;
import edu.hm.bugproducer.models.Disc;
import edu.hm.bugproducer.restAPI.MediaServiceResult;
import javafx.util.Pair;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static edu.hm.bugproducer.restAPI.MediaServiceResult.*;
import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_NOT_FOUND;
import static edu.hm.bugproducer.restAPI.MediaServiceResult.MSR_OK;

public class MediaServiceImpl implements MediaService {


    private static final String URLVERIFY = "http://localhost:8082/auth/verify/";
    private static final String URL_BOOKS = "http://localhost:8080/shareit/media/books/";
    private static final String URL_DISCS = "http://localhost:8080/shareit/media/discs/";

    public static List<Book> books = new ArrayList<>();
    public static List<Disc> discs = new ArrayList<>();

    @Override
    public MediaServiceResult addBook(Book book) {
        MediaServiceResult mediaServiceResult = MSR_INTERNAL_SERVER_ERROR;

        System.out.println("Ich füge ein Wunderbares Buch hinzu !!!!");

        if (book == null) {
            //todo Author und ISBN auf Null prüfen!!! DAS gleiche brauchen wir auch bei der Disc
            mediaServiceResult = MSR_NO_CONTENT;
        } else if (book.getAuthor().isEmpty() || book.getTitle().isEmpty() || book.getIsbn().isEmpty()) {
            mediaServiceResult = MSR_BAD_REQUEST;
        } else if (!Isbn.isValid(book.getIsbn())) {
            mediaServiceResult = MSR_BAD_REQUEST;
        } else {
            if (books.isEmpty()) {
                mediaServiceResult = MSR_OK;
                books.add(book);
            } else {

                ListIterator<Book> lir = books.listIterator();

                while (lir.hasNext()) {
                    if (lir.next().getIsbn().equals(book.getIsbn())) {
                        mediaServiceResult = MSR_BAD_REQUEST;
                    } else {
                        lir.add(book);
                        mediaServiceResult = MSR_OK;
                    }
                }
            }
        }

        return mediaServiceResult;
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
        System.out.println("gewtBooks: start");
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet verify = new HttpGet(URLVERIFY + token);
        HttpResponse authResponse = client.execute(verify);

        if (authResponse.getStatusLine().getStatusCode()==MSR_OK.getCode()){
            HttpGet request = new HttpGet(URL_BOOKS);
            HttpResponse shareItResponse = client.execute(request);
            if (shareItResponse.getStatusLine().getStatusCode()==MSR_OK.getCode()){
                return shareItResponse.getEntity();
            }
        }
        System.out.println("gewtBooks: end");
        return authResponse.getEntity();
    }

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
    public List<Disc> getDiscs() {



        return discs;
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
