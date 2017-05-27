package edu.hm.bugproducer.restAPI.media;

import edu.hm.bugproducer.models.Book;
import edu.hm.bugproducer.models.Disc;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;

/**
 * Created by Johannes Arzt on 25.04.17.
 */

public interface MediaService {

    HttpResponse addBook(String token, Book book) throws IOException;

    HttpResponse addDisc(String token, Disc disc) throws IOException;

    HttpEntity getBooks(String token) throws IOException;

    HttpEntity getDiscs(String token) throws IOException;

    HttpResponse getBook(String token, String isbn) throws IOException;

    HttpResponse getDisc(String token, String barcode) throws IOException;

    HttpResponse updateBook(String token, String isbn, Book book) throws IOException;

    HttpResponse updateDisc(String token, String barcode, Disc disc) throws IOException;

}
