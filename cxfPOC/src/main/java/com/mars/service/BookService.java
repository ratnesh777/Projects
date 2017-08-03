package com.mars.service;

import java.util.List;

import com.mars.models.Book;

public interface BookService {

    Book createBook(Book book);

    Book findBookById(String bookId);
    Book update(Book book, String bookId);
    
    void deleteBook(String id);

    List<Book> findAllBooks();
    
    Book findBookByTitle(String title) ;
    

}
