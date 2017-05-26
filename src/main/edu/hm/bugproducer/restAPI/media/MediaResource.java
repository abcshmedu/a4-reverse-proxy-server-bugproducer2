
package edu.hm.bugproducer.restAPI.media;

import edu.hm.bugproducer.models.Book;
import edu.hm.bugproducer.models.Disc;
import edu.hm.bugproducer.restAPI.MediaServiceResult;
import javafx.util.Pair;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

@Path("/media")
public class MediaResource {
    private static final int RESPONSECODE = 200;


    private MediaServiceImpl mediaService = new MediaServiceImpl();

    public MediaResource() {
    }

    @GET
    @Path("{token}/books/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@PathParam("token") String token) throws IOException {
        System.out.println("getBooks");
        HttpEntity httpEntityBooks = mediaService.getBooks(token);
        System.err.println("HTTP: " + httpEntityBooks);
        return Response
                .status(RESPONSECODE)
                .entity(httpEntityBooks.getContent())
                .build();
    }

    @GET
    @Path("{token}/discs/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs(@PathParam("token") String token) throws IOException {
        System.out.println("getDiscs");
        HttpEntity httpEntityDiscs = mediaService.getDiscs(token);
        System.err.println("HTTP: " + httpEntityDiscs);

        return Response
                .status(RESPONSECODE)
                .entity(httpEntityDiscs.getContent())
                .build();
    }


    @POST
    @Path("/discs/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDiscs(Disc disc) {
        //ToDo Change Name
        MediaServiceResult result = mediaService.addDisc(disc);
        return Response
                .status(result.getCode())
                .build();
    }

    @POST
    @Path("{token}/books/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBooks(@PathParam("token") String token, Book book) throws IOException {
        //System.out.println("createBooks" + book.getAuthor());
        //ToDo Change Name
        HttpResponse result = mediaService.addBook(token,book);

        return Response
                .status(result.getStatusLine().getStatusCode())
                .build();
    }


   /* @GET
    @Path("/books/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("isbn") String isbn) {
        System.out.println("getBook");
        Pair<MediaServiceResult, Book> myResult = mediaService.getBook(isbn);
        System.out.println(isbn);
        return Response
                .status(myResult.getKey().getCode())
                .entity(myResult.getValue())
                .build();
    }
*/
    /*@GET
    @Path("/discs/{barcode}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDisc(@PathParam("barcode") String barcode) {
        System.out.println("getBook");
        Pair<MediaServiceResult, Disc> myResult = mediaService.getDisc(barcode);
        System.out.println(barcode);
        return Response
                .status(myResult.getKey().getCode())
                .entity(myResult.getValue())
                .build();
    }*/

    @PUT
    @Path("/books/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("isbn") String isbn, Book book) {
        System.out.println("updateBook: " + isbn);
        System.out.println("Title: " + book.getTitle() + "Author: " + book.getAuthor() + " ISBN: " + book.getIsbn());
        MediaServiceResult result = mediaService.updateBook(isbn, book);
        System.err.println("RESULT" + result.getCode());
        return Response
                .status(result.getCode())
                .build();
    }

    @PUT
    @Path("/discs/{barcode}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDisc(@PathParam("barcode") String barcode, Disc disc) {
        MediaServiceResult result = mediaService.updateDisc(barcode, disc);
        System.err.println("RESULT" + result.getCode());
        return Response
                .status(result.getCode())
                .build();
    }
}
