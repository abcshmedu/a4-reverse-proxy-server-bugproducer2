package edu.hm.bugproducer.restAPI.media;

import edu.hm.bugproducer.models.Book;
import edu.hm.bugproducer.models.Disc;
import org.apache.http.HttpResponse;

import java.io.IOException;



public interface MediaService {
    /**
     * addBook method.
     * adds a book with token
     * @param token unique string
     * @param book book object
     * @return a HttpResponse
     * @throws IOException by wrong input
     */
    HttpResponse addBook(String token, Book book) throws IOException;

    /**
     * addDisc method.
     * adds a disc with token
     * @param token unique string
     * @param disc disc object
     * @return a HttpResponse
     * @throws IOException by wrong input
     */
    HttpResponse addDisc(String token, Disc disc) throws IOException;

    /**
     * getBooks method.
     * get the books with the token
     * @param token unique string
     * @return HttpResponse
     * @throws IOException by wrong input
     */
    HttpResponse getBooks(String token) throws IOException;

    /**
     * getDiscs method.
     * get the discs with the token
     * @param token unique string
     * @return HttpResponse
     * @throws IOException by wrong input
     */
    HttpResponse getDiscs(String token) throws IOException;

    /**
     * getBook method.
     * get a specific book with the isbn and token
     * @param token unique string
     * @param isbn unique string of book
     * @return HttpResponse
     * @throws IOException by wrong input
     */
    HttpResponse getBook(String token, String isbn) throws IOException;

    /**
     * getDisc method.
     * get a specific disc with the barcode and token
     * @param token unique string
     * @param barcode unique string of disc
     * @return HttpResponse
     * @throws IOException by wrong input
     */
    HttpResponse getDisc(String token, String barcode) throws IOException;

    /**
     * updateBook method.
     * update the book that you get with the isbn and put the information of the new book into it
     * @param token unique string
     * @param isbn unique string of book
     * @param book new book object
     * @return HttpResponse
     * @throws IOException by wrong input
     */
    HttpResponse updateBook(String token, String isbn, Book book) throws IOException;

    /**
     * updateDisc method.
     * update the disc that you get with the barcode and put the information of the new disc into it
     * @param token unique string
     * @param barcode unique string of disc
     * @param disc new disc object
     * @return HttpResponse
     * @throws IOException by wrong input
     */
    HttpResponse updateDisc(String token, String barcode, Disc disc) throws IOException;

}
