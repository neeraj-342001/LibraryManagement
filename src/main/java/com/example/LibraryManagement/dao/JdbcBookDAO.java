package com.example.LibraryManagement.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.LibraryManagement.model.Book;

@Repository
public class JdbcBookDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Book> bookRowMapper;

    public JdbcBookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRowMapper = new BeanPropertyRowMapper<>(Book.class);
    }

    public List<Book> searchBooksByTitle(String title) {
        try {
            String sql = "SELECT * from book WHERE title LIKE ?";
            return jdbcTemplate.query(sql, bookRowMapper, "%" + title + "%");
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error searching books by title: " + e.getMessage(), e);
        }
    }

    public List<Book> searchBooksByAuthor(String author) {
        try {
            String sql = "SELECT * FROM book WHERE author LIKE ?";
            return jdbcTemplate.query(sql, bookRowMapper, "%" + author + "%");
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error searching books by author: " + e.getMessage(), e);
        }
    }

    public List<Book> searchBooksByCategory(String category) {
        try {
            String sql = "SELECT * FROM book WHERE category LIKE ?";
            return jdbcTemplate.query(sql, bookRowMapper, "%" + category + "%");
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error searching books by category: " + e.getMessage(), e);
        }
    }

    public List<Book> searchBooksByPublicationDate(String publicationDate) {
        try {
            String sql = "SELECT * FROM book WHERE publication_date LIKE ?";
            return jdbcTemplate.query(sql, bookRowMapper, "%" + publicationDate + "%");
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error searching books by publication date: " + e.getMessage(), e);
        }
    }

    public List<Book> getBooksCheckedOutByMember(int memberID) {
        try {
            String sql = "SELECT b.* FROM book b " +
                    "JOIN allocate a ON b.book_ISBN = a.book_ISBN " +
                    "WHERE a.member_id = ?";
            return jdbcTemplate.query(sql, bookRowMapper, memberID);
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error retrieving books checked out by member: " + e.getMessage(), e);
        }
    }

    public List<Book> getBooksReservedByMember(int memberId) {
        try {
            String sql = "SELECT b.* FROM book b " +
                    "JOIN reserve r ON b.book_ISBN = r.book_ISBN " +
                    "WHERE r.member_id = ?";
            return jdbcTemplate.query(sql, bookRowMapper, memberId);
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error retrieving books reserved by member: " + e.getMessage(), e);
        }
    }

    public int getBookCount(int bookISBN) {
        try {
            String sql = "SELECT count FROM book WHERE book_ISBN = ?";
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, bookISBN);
            // Check for null before unboxing
            return (result != null) ? result : 0;
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error getting book count: " + e.getMessage(), e);
        }
    }

    public void updateBookCount(int bookISBN, int delta) {
        try {
            String sql = "UPDATE book SET count = count + ? WHERE book_ISBN = ?";
            jdbcTemplate.update(sql, delta, bookISBN);
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error updating book count: " + e.getMessage(), e);
        }
    }

    public Book findById(int bookISBN) {
        try {
            String sql = "SELECT * FROM book WHERE book_ISBN = ?";
            return jdbcTemplate.queryForObject(sql, bookRowMapper, bookISBN);
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error retrieving book by ID: " + e.getMessage(), e);
        }
    }

    public void save(Book book) {
        try {
            String sql = "INSERT INTO book (book_ISBN, title, author, category, publication_date, rack_number, count) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    book.getBook_ISBN(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getPublication_date(),
                    book.getRack_number(),
                    book.getCount());
        } catch (DataAccessException e) {
            throw new MyDataAccessException("Error saving book: " + e.getMessage(), e);
        }
    }

    public static class MyDataAccessException extends RuntimeException {
        public MyDataAccessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
