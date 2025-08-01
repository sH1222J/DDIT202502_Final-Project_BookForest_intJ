package com.bookforest.project_bookforest_intj.book.controller;

import com.bookforest.project_bookforest_intj.book.entity.Book;
import com.bookforest.project_bookforest_intj.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public String bookList(@RequestParam(required = false) String category, Model model) {
        List<Book> books = null;

                /*
                bookService.findAllBooks();
        if (category != null && !category.isEmpty()) {
            books = books.stream()
                         .filter(book -> category.equals(book.getCategory()))
                         .collect(Collectors.toList());
        }
        */
        model.addAttribute("books", books);
        return "book/list";
    }

    @GetMapping("/detail/{bookId}")
    public String bookDetail(@PathVariable Long bookId, Model model) {
        Book book = null;
                /*
                bookService.findBookById(bookId);
                 */
        model.addAttribute("book", book);
        return "book/detail";
    }
}
