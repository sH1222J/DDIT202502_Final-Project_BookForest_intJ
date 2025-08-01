package com.bookforest.project_bookforest_intj.book.service;

import com.bookforest.project_bookforest_intj.book.entity.Book;
import com.bookforest.project_bookforest_intj.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    //@Autowired
    //private BookRepository bookRepository;

    /*
    public List<Book> findAllBooks() {
        return null;
    }

    public Book findBookById(Long bookId) {
        return null;
    }
     */
}
