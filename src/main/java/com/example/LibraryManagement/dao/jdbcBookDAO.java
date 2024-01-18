package com.example.LibraryManagement.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.LibraryManagement.model.Book;

@Repository
public class jdbcBookDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Book> bookRowMapper;

    public jdbcBookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRowMapper = new BeanPropertyRowMapper<>(Book.class);
    }

    public List<Book> searchBooksByTitle(String title) {
        String sql = "SELECT * from book WHERE title LIKE ?";
        return jdbcTemplate.query(sql, bookRowMapper, "%" + title + "%");
    }

    public List<Book> searchBooksByAuthor(String author) {
        String sql = "SELECT * FROM book WHERE author LIKE ?";
        return jdbcTemplate.query(sql, bookRowMapper, "%" + author + "%");
    }

    public List<Book> searchBooksByCategory(String category) {
        String sql = "SELECT * FROM book WHERE category LIKE ?";
        return jdbcTemplate.query(sql, bookRowMapper, "%" + category + "%");
    }

    public List<Book> searchBooksByPublicationDate(String publicationDate) {
        String sql = "SELECT * FROM book WHERE publication_date LIKE ?";
        return jdbcTemplate.query(sql, bookRowMapper, "%" + publicationDate + "%");
    }

    public List<Book> getBooksCheckedOutByMember(int memberID) {
        String sql = "SELECT b.* FROM book b " +
                "JOIN allocate a ON b.book_ISBN = a.book_ISBN " +
                "WHERE a.member_id = ?";
        return jdbcTemplate.query(sql, bookRowMapper, memberID);
    }

    public List<Book> getBooksReservedByMember(int memberId) {
        String sql = "SELECT b.* FROM book b " +
                "JOIN reserve r ON b.book_ISBN = r.book_ISBN " +
                "WHERE r.member_id = ?";
        return jdbcTemplate.query(sql, bookRowMapper, memberId);
    }

    public void save(Book book) {
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
    }
}
