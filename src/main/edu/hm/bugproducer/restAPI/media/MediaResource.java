
package edu.hm.bugproducer.restAPI.media;

import edu.hm.bugproducer.models.Book;
import edu.hm.bugproducer.models.Disc;
import edu.hm.bugproducer.restAPI.MediaServiceResult;
import org.apache.http.HttpResponse;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;



/**
 * MediaResource Class.
 *
 * @author Mark Tripolt
 * @author Johannes Arzt
 * @author Tom Maier
 * @author Patrick Kuntz
 */
@Path("/media")
public class MediaResource {
    /**
     * response code for OK
     */
    private static final int RESPONSECODE = 200;
    /**
     * mediaService variable for the media service implementation
     */
    private MediaServiceImpl mediaService = new MediaServiceImpl();

    /**
     * MediaResource Constructor.
     */
    public MediaResource() {
    }

    /**
     * getBooks method.
     * get the books with the token by using HTTP verb GET
     * @param token unique string
     * @return status code with content
     * @throws IOException by wrong input
     */
    @GET
    @Path("{token}/books/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@PathParam("token") String token) throws IOException {
        System.out.println("getBooks");
        HttpResponse response = mediaService.getBooks(token);
        System.err.println("HTTP: " + response);
        return Response
                .status(response.getStatusLine().getStatusCode())
                .entity(response.getEntity().getContent())
                .build();
    }

    /**
     * getDiscs metho.d
     * get discs with the token by using HTTP verb GET.
     * @param token unique string
     * @return status code with content or just a status code
     * @throws IOException by wrong input
     */
    @GET
    @Path("{token}/discs/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs(@PathParam("token") String token) throws IOException {
        System.out.println("getDiscs");
        HttpResponse response = mediaService.getDiscs(token);

            return Response
                    .status(response.getStatusLine().getStatusCode())
                    .entity(response.getEntity().getContent())
                    .build();


        }




    /**
     * createDiscs method.
     * creating a disc with a token by using the HTTP verb POST.
     * @param token unique string
     * @param disc disc object
     * @return a status code
     * @throws IOException by wrong input
     */
    @POST
    @Path("{token}/discs/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDiscs(@PathParam("token") String token, Disc disc) throws IOException {
        HttpResponse result = mediaService.addDisc(token, disc);
        return Response
                .status(result.getStatusLine().getStatusCode())
                .build();
    }

    /**
     * createBooks method.
     * creating a books with a token by using the HTTP verb POST.
     * @param token unique string
     * @param book book object
     * @return a status code
     * @throws IOException by wrong input
     */
    @POST
    @Path("{token}/books/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBooks(@PathParam("token") String token, Book book) throws IOException {
        HttpResponse result = mediaService.addBook(token, book);
        return Response
                .status(result.getStatusLine().getStatusCode())
                .build();
    }


    /**
     * getBook method.
     * gets a book with token and isbn by using the HTTP verb GET.
     * @param token unique string
     * @param isbn unique string of a book
     * @return status code and content
     * @throws IOException by wrong input
     */
    @GET
    @Path("{token}/books/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("token") String token, @PathParam("isbn") String isbn) throws IOException {
        System.out.println("getBook");
        HttpResponse result = mediaService.getBook(token, isbn);
        System.out.println(isbn);
        return Response
                .status(result.getStatusLine().getStatusCode())
                .entity(result.getEntity().getContent())
                .build();
    }
    /**
     * getDisc method.
     * gets a disc with token and barcode by using the HTTP verb GET.
     * @param token unique string
     * @param barcode unique string of a disc
     * @return status code and content
     * @throws IOException by wrong input
     */
    @GET
    @Path("{token}/discs/{barcode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisc(@PathParam("token") String token, @PathParam("barcode") String barcode) throws IOException {
        System.out.println("getDisc");
        HttpResponse result = mediaService.getDisc(token, barcode);
        System.out.println(barcode);
        return Response
                .status(result.getStatusLine().getStatusCode())
                .entity(result.getEntity().getContent())
                .build();
    }

    /**
     * updateBook method.
     * updating the book with the isbn and put in the information of book
     * @param token unique string
     * @param isbn unique string of book
     * @param book book object
     * @return status code
     * @throws IOException by wrong input
     */
    @PUT
    @Path("{token}/books/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("token") String token, @PathParam("isbn") String isbn, Book book) throws IOException {
        System.out.println("updateBook: " + isbn);
        System.out.println("Title: " + book.getTitle() + "Author: " + book.getAuthor() + " ISBN: " + book.getIsbn());
        HttpResponse result = mediaService.updateBook(token, isbn, book);
        System.err.println("RESULT" + result.getStatusLine().getStatusCode());
        return Response
                .status(result.getStatusLine().getStatusCode())
                .build();
    }
    /**
     * updateDisc method.
     * updating the disc with the barcode and put in the information of disc
     * @param token unique string
     * @param barcode unique string of disc
     * @param disc disc object
     * @return status code
     * @throws IOException by wrong input
     */
    @PUT
    @Path("{token}/discs/{barcode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDisc(@PathParam("token") String token, @PathParam("barcode") String barcode, Disc disc) throws IOException {
        HttpResponse result = mediaService.updateDisc(token, barcode, disc);
        System.err.println("RESULT" + result.getStatusLine().getStatusCode());
        return Response
                .status(result.getStatusLine().getStatusCode())
                .build();
    }
}
