package edu.hm.bugproducer.restAPI.media;

import edu.hm.bugproducer.models.Book;
import edu.hm.bugproducer.models.Disc;
import edu.hm.bugproducer.restAPI.MediaServiceResult;
import javafx.util.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.List;

/**
 * Created by Johannes Arzt on 25.04.17.
 */

public interface MediaService {

    HttpResponse addBook(String token, Book book) throws IOException;

    HttpResponse addDisc(String token,Disc disc) throws IOException;

    HttpEntity getBooks(String token) throws IOException;

    HttpEntity getDiscs(String token)throws IOException;

    Pair<MediaServiceResult, Book> getBook(String isbn);

    Pair<MediaServiceResult, Disc> getDisc(String barcode);

    HttpResponse updateBook(String token, String isbn, Book book) throws IOException;

    HttpResponse updateDisc(String token, String barcode, Disc disc) throws IOException;

}
