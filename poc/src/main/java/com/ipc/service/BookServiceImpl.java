package com.ipc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ipc.repository.Book;
import com.ipc.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    BookRepository bookRepository;
    @Override
    public Book createBook(Book book)
    {
        return bookRepository.save(book);
        
    }

    @Override
    @Cacheable (value = "book", key = "#id")
    public Book findBookById(String bookId)
    {
        return bookRepository.findOne(Integer.parseInt(bookId));
    }

    @Override
    @CachePut(value = "book", key = "#id")
    public Book update(Book book, String id)
    {
        book.setId(Integer.parseInt(id));
        return bookRepository.save(book);
    }

    @Override
    @CacheEvict (value = "book", key = "#id")
    public void deleteBook(String id)
    {
         bookRepository.delete(Integer.parseInt(id));
    }

    @Override
    public List<Book> findAllBooks()
    {
        return (List<Book>) bookRepository.findAll();
    }

    @Override
    public Book findBookByTitle(String title)
    {
        Book insertedBook = bookRepository.findByTitle(title);
        return insertedBook;
    }

   

}
