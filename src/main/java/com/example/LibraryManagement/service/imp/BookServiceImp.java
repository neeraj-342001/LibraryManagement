package com.example.LibraryManagement.service.imp;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LibraryManagement.dao.JdbcAllocateDAO;
import com.example.LibraryManagement.dao.JdbcBookDAO;
import com.example.LibraryManagement.dao.JdbcMemberDAO;
import com.example.LibraryManagement.dao.JdbcReserveDAO;
import com.example.LibraryManagement.model.Allocate;
import com.example.LibraryManagement.model.Book;
import com.example.LibraryManagement.model.Member;
import com.example.LibraryManagement.model.Reserve;
import com.example.LibraryManagement.service.inter.BookService;

@Service
public class BookServiceImp implements BookService {

    private final JdbcBookDAO bookDAO;
    private final JdbcAllocateDAO allocateDAO;
    private final JdbcMemberDAO memberDAO;
    private final JdbcReserveDAO reserveDAO;

    @Autowired
    public BookServiceImp(JdbcBookDAO bookDAO, JdbcAllocateDAO allocateDAO, JdbcMemberDAO memberDAO, JdbcReserveDAO reserveDAO) {
        this.bookDAO = bookDAO;
        this.allocateDAO = allocateDAO;
        this.memberDAO = memberDAO;
        this.reserveDAO = reserveDAO;
    }


    @Override
    public void saveBook(Book book) {

        bookDAO.save(book);
        handleBookReserveAllocation(book.getBook_ISBN());
    }

    @Override
    public List<Book> searchBooksByTitle(String title) {
        return bookDAO.searchBooksByTitle(title);
    }

    @Override
    public List<Book> searchBooksByAuthor(String author) {
        return bookDAO.searchBooksByAuthor(author);
    }

    @Override
    public List<Book> searchBooksByCategory(String category) {
        return bookDAO.searchBooksByCategory(category);
    }

    @Override
    public List<Book> searchBooksByPublicationDate(String publicationDate) {
        return bookDAO.searchBooksByPublicationDate(publicationDate);
    }

    @Override
    public List<Member> getMembersWhoTookBook(int bookISBN) {
        return memberDAO.getMembersWhoTookBook(bookISBN);
    }

    @Override
    public void borrowBook(int memberId, int bookISBN) {

        if(canBorrow(memberId,bookISBN)) {

            if(isBookAvailable(bookISBN)) {

                updateBookCount(bookISBN, -1);

                Allocate allocate = Allocate.build(0, memberDAO.findById(memberId),
                        bookDAO.findById(bookISBN), LocalDate.now().plusDays(10));

                allocateDAO.save(allocate);
            }
            else {
                if(!reserveDAO.existsByMemberIdAndBookISBN(memberId, bookISBN)) {
                    Reserve reserve = Reserve.build(0,memberDAO.findById(memberId),bookDAO.findById(bookISBN));
                    reserveDAO.save(reserve);
                    System.out.println("The book has been reserved for you.");
                } else {
                    System.out.println("The book is already reserved by you.");
                }
                throw new RuntimeException("Book is not Available for Borrowing");
            }
        }
        else {
            throw new RuntimeException("Member is not eligible for Borrowing");
        }
    }

    @Override
    public void returnBook(int memberId, int bookISBN) {
        if(hasBorrowedBook(memberId,bookISBN)) {
            updateBookCount(bookISBN,1);
            allocateDAO.deleteByMemberIdAndBookISBN(memberId,bookISBN);
            handleBookReserveAllocation(bookISBN);
        }
        else {
            throw new RuntimeException("Member has not borrowed the specified book");
        }
    }

    private boolean canBorrow(int memberId,int bookISBN) {
        List<Allocate> overdueAllocations = allocateDAO.findOverdueAllocationsByMemberId(memberId);
        int cnt = allocateDAO.countBooksForMember(memberId);

        return overdueAllocations.isEmpty() && cnt<5 && !hasBorrowedBook(memberId,bookISBN);
    }

    private boolean isBookAvailable(int bookISBN) {
        return bookDAO.getBookCount(bookISBN) > 0;
    }

    private void updateBookCount(int bookISBN,int delta) {
        bookDAO.updateBookCount(bookISBN,delta);
    }

    private boolean hasBorrowedBook(int memberId, int bookISBN) {
        return allocateDAO.existsByMemberIdAndBookISBN(memberId,bookISBN);
    }

    private void handleBookReserveAllocation(int bookISBN) {
        if(reserveDAO.existsByBookISBN(bookISBN)) {
            Reserve firstReserve = reserveDAO.getFirstReservation(bookISBN);
            int memberId = firstReserve.getMember().getMember_id();

            Allocate allocate = Allocate.build(0, memberDAO.findById(memberId),
                    bookDAO.findById(bookISBN), LocalDate.now().plusDays(10));

            allocateDAO.save(allocate);
            updateBookCount(bookISBN, -1);

            reserveDAO.deleteByMemberIdAndBookISBN(memberId,bookISBN);
            System.out.println("The book has been allocated to you from the reserve.");
        }
    }
}
