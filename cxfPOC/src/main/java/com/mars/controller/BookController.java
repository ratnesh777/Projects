package com.mars.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.mars.models.Book;

@Path("/book")
@Produces({"application/json"})
public class BookController
{

    @POST
  //  @Path("/book")
    @Consumes({"application/json"})
    public Response createBook(Book book)
    {
        return Response.ok(book).build();
    }

    @GET
    @Path("/{id}")
    public Book getBookId(@PathParam(value = "id") String id)
    {
        // bookService.findBookById(id);
        Book book = new Book();
        book.setId(1);
        book.setAuthor("Ratnesh");
        book.setTitle("Math");
        book.setDescription("Math Learning Book");
        return book;

    }

    @GET
    //@Path("/book")
    public List<Book> getBookList()
    {
        // return bookService.findAllBooks();
        return null;
    }

}

