
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
        HttpResponse response = mediaService.getBooks(token);
        System.err.println("HTTP: " + response);
        return Response
                .status(response.getStatusLine().getStatusCode())
                .entity(response.getEntity().getContent())
                .build();
    }

    @GET
    @Path("{token}/discs/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscs(@PathParam("token") String token) throws IOException {
        System.out.println("getDiscs");
        HttpResponse response = mediaService.getDiscs(token);

        if (response.getStatusLine().getStatusCode()==MediaServiceResult.MSR_OK.getCode()){
            return Response
                    .status(response.getStatusLine().getStatusCode())
                    .entity(response.getEntity().getContent())
                    .build();
        }else{
            return Response
                    .status(response.getStatusLine().getStatusCode())
                    .build();
        }


    }


    @POST
    @Path("{token}/discs/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDiscs(@PathParam("token") String token, Disc disc) throws IOException {
        HttpResponse result = mediaService.addDisc(token, disc);
        return Response
                .status(result.getStatusLine().getStatusCode())
                .build();
    }

    @POST
    @Path("{token}/books/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBooks(@PathParam("token") String token, Book book) throws IOException {
        HttpResponse result = mediaService.addBook(token, book);
        return Response
                .status(result.getStatusLine().getStatusCode())
                .build();
    }


    @GET
    @Path("{token}/books/{isbn}")
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

    @GET
    @Path("{token}/discs/{barcode}")
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

    @PUT
    @Path("{token}/books/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("token") String token, @PathParam("isbn") String isbn, Book book) throws IOException {
        System.out.println("updateBook: " + isbn);
        System.out.println("Title: " + book.getTitle() + "Author: " + book.getAuthor() + " ISBN: " + book.getIsbn());
        HttpResponse result = mediaService.updateBook(token, isbn, book);
        System.err.println("RESULT" + result.getStatusLine().getStatusCode());
        return Response
                .status(result.getStatusLine().getStatusCode())
                .build();
    }

    @PUT
    @Path("{token}/discs/{barcode}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDisc(@PathParam("token") String token, @PathParam("barcode") String barcode, Disc disc) throws IOException {
        HttpResponse result = mediaService.updateDisc(token, barcode, disc);
        System.err.println("RESULT" + result.getStatusLine().getStatusCode());
        return Response
                .status(result.getStatusLine().getStatusCode())
                .build();
    }
}
