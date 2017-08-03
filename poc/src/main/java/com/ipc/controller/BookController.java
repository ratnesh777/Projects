package com.ipc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ipc.exception.InvalidRequestException;
import com.ipc.repository.Book;
import com.ipc.service.BookService;
import com.ipc.util.APIUtilConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value=APIUtilConstant.BOOK_API_END_POINT,produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Book APIs Detail")
public class BookController {

	@Value("${base.path.uri}")
	private String BASE_PATH_URI;
	
	@Autowired
	public BookService bookService;
	
    @ApiOperation(nickname = "Create Book", value = "Creates a Book", notes = "")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Book.class),
            @ApiResponse(code = 422, message = "Invalid Request"),
            @ApiResponse(code = 500, message = "Failure") ,
    		@ApiResponse(code = 400, message = "Bad Request")}) 
	
	  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Book> createBook(@RequestBody  Book book, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new InvalidRequestException("Invalid create request", bindingResult);
		}
		book = bookService.createBook(book);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Location", BASE_PATH_URI+ APIUtilConstant.EMP_API_END_POINT+ "/" + book.getId());
		return new ResponseEntity<Book>(book, httpHeaders, HttpStatus.CREATED);
	}
	
    @ApiOperation(nickname = "Retrieves Book detail by id", value = "Retrieves Book detail by id", notes = "")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Book.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Book getBookId(@ApiParam(name="id", value="The Id of the Book to be viewed", required=true)@PathVariable String id)  {
		
		
      return bookService.findBookById(id);
        
	}
    
    
    @ApiOperation(nickname = "Retrieves Book detail by title", value = "Retrieves Book detail by id", notes = "")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Book.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
    @RequestMapping(value = "title/{title}", method = RequestMethod.GET)
    public Book getBookByTitle(@ApiParam(name="title", value="The Book to be viewed by title", required=true)@PathVariable String title)  {
        
        return bookService.findBookByTitle(title);
        
    }
    
    
	
    @ApiOperation(nickname = "Update Book", value = "Update Book", notes = "")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Book.class),
            @ApiResponse(code = 422, message = "Invalid Request"),
            @ApiResponse(code = 500, message = "Failure") ,
    		@ApiResponse(code = 400, message = "Bad Request")}) 
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_VALUE)
	public Book updateBook(@ApiParam(name="id", value="The Id of the Employee to be viewed", required=true)  @PathVariable String id,@RequestBody  Book book, BindingResult bindingResult) {
	
		if (bindingResult.hasErrors()) {
			throw new InvalidRequestException("Invalid create request", bindingResult);
		}
		return bookService.update(book, id);
	}
	
	
    @ApiOperation(nickname = "Retrieves Book detail", value = "Retrieves Book detail ", notes = "")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = "Success", response = Book.class),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Failure")}) 
	@RequestMapping(method = RequestMethod.GET)
	public List<Book> getBookList() {
	
			return bookService.findAllBooks();
	}
	
}
