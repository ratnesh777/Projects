package com.mars.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mars.models.Book;



@Service
public class BookServiceImpl implements BookService{

    public com.mars.models.Book createBook(Book book)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public com.mars.models.Book findBookById(String bookId)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public com.mars.models.Book update(Book book, String bookId)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteBook(String id)
    {
        // TODO Auto-generated method stub
        
    }

    public List<com.mars.models.Book> findAllBooks()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public com.mars.models.Book findBookByTitle(String title)
    {
        // TODO Auto-generated method stub
        return null;
    }

  /*  @Autowired
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
*/
   

}
