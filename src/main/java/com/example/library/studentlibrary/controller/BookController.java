package com.example.library.studentlibrary.controller;

import com.example.library.studentlibrary.models.Author;
import com.example.library.studentlibrary.models.Book;
import com.example.library.studentlibrary.models.Genre;
import com.example.library.studentlibrary.repositories.BookRepository;
import com.example.library.studentlibrary.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

@Autowired
        BookService bookService;
PostMapping("/create/")
    public ResponseEntity<String> createBook(@RequestParam(required = true) Book book)
    {
        bookService.createBook(book);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }
    @GetMapping("/book/")
    public ResponseEntity getBooks(@RequestParam(value = "genre", required = false) String genre,
                                   @RequestParam(value = "available", required = false, defaultValue = "false") boolean available,
                                   @RequestParam(value = "author", required = false) String author){

        List<Book> bookList = bookService.getBooks(genre, available,author);

        return new ResponseEntity<>(bookList, HttpStatus.OK);

    }
}
