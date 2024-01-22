package com.example.LibraryManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.LibraryManagement.model.Book;
import com.example.LibraryManagement.model.Member;
import com.example.LibraryManagement.service.inter.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;


    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveBook(@RequestBody Book book) {
        try {
            bookService.saveBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body("Book saved successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving the book");
        }
    }

    @GetMapping("/search/title/{title}")
    public ResponseEntity<List<Book>> searchBooksByTitle(@PathVariable String title) {
        List<Book> books = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/author/{author}")
    public ResponseEntity<List<Book>> searchBooksByAuthor(@PathVariable String author) {
        List<Book> books = bookService.searchBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/category/{category}")
    public ResponseEntity<List<Book>> searchBooksByCategory(@PathVariable String category) {
        List<Book> books = bookService.searchBooksByCategory(category);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search/publication/{publication}")
    public ResponseEntity<List<Book>> searchBooksByPublicationDate(@PathVariable String publication) {
        List<Book> books = bookService.searchBooksByPublicationDate(publication);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/members/{bookISBN}")
    public ResponseEntity<List<Member>> getMembersWhoTookBook(@PathVariable int bookISBN) {
        try {
            List<Member> members = bookService.getMembersWhoTookBook(bookISBN);
            return new ResponseEntity<>(members, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestParam int memberId, @RequestParam int bookISBN) {
        try {
            bookService.borrowBook(memberId, bookISBN);
            return new ResponseEntity<>("Book borrowed successfully", HttpStatus.OK);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam int memberId, @RequestParam int bookISBN) {
        try {
            bookService.returnBook(memberId,bookISBN);
            return new ResponseEntity<>("Book returned successfully", HttpStatus.OK);
        }
        catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
