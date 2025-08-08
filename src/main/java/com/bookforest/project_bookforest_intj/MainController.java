package com.bookforest.project_bookforest_intj;

import com.bookforest.project_bookforest_intj.book.entity.Book;
import com.bookforest.project_bookforest_intj.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final BookService bookService;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("books", books);
        return "index";
    }
}
