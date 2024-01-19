package com.example.LibraryManagement.service.inter;

import java.util.List;

import com.example.LibraryManagement.model.Book;
import com.example.LibraryManagement.model.Member;

public interface BookService {

    void saveBook(Book book);

    List<Book> searchBooksByTitle(String title);

    List<Book> searchBooksByAuthor(String author);

    List<Book> searchBooksByCategory(String category);

    List<Book> searchBooksByPublicationDate(String publicationDate);


    List<Member> getMembersWhoTookBook(int bookISBN);

    void borrowBook(int memberId, int bookISBN);

    void returnBook(int memberId, int bookISBN);
}
