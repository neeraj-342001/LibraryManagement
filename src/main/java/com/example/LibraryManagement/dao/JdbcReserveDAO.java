package com.example.LibraryManagement.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.LibraryManagement.model.Reserve;

@Repository
public class JdbcReserveDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Reserve> reserveRowMapper;

    public JdbcReserveDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reserveRowMapper = new BeanPropertyRowMapper<>(Reserve.class);
    }

    public boolean existsByMemberIdAndBookISBN(int memberId, int bookISBN) {
        try {
            String sql = "SELECT COUNT(*) FROM reserve WHERE member_id = ? AND book_ISBN = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, memberId, bookISBN);
            return (count != null && count > 0);
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error checking reservation existence by member ID and book ISBN: " + e.getMessage(), e);
        }
    }

    public Reserve getFirstReservation(int bookISBN) {
        try {
            String sql = "SELECT * FROM reserve WHERE book_ISBN = ? ORDER BY reverse_id LIMIT 1";
            return jdbcTemplate.queryForObject(sql, reserveRowMapper, bookISBN);
        }
        catch (EmptyResultDataAccessException e) {
            return null; // No reservation found for the specified book
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error retrieving first reservation by book ISBN: " + e.getMessage(), e);
        }
    }

    public boolean existsByBookISBN(int bookISBN) {
        try {
            String sql = "SELECT COUNT(*) FROM reserve WHERE book_ISBN = ?";
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, bookISBN);
            return (count != null && count > 0);
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error checking reservation existence by book ISBN: " + e.getMessage(), e);
        }
    }

    public void deleteByMemberIdAndBookISBN(int memberId, int bookISBN) {
        try {
            String sql = "DELETE FROM reserve WHERE member_id = ? AND book_ISBN = ?";
            jdbcTemplate.update(sql, memberId, bookISBN);
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error deleting reservation by member ID and book ISBN: " + e.getMessage(), e);
        }
    }

    public List<Reserve> getReserveByMemberId(int memberId) {
        try {
            String sql = "SELECT * FROM reserve WHERE member_id = ?";
            return jdbcTemplate.query(sql, reserveRowMapper, memberId);
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error retrieving reservations by member ID: " + e.getMessage(), e);
        }
    }

    public void save(Reserve reserve) {
        try {
            String sql = "INSERT INTO reserve (member_id, book_ISBN) VALUES (?, ?)";
            jdbcTemplate.update(sql, reserve.getMember().getMember_id(), reserve.getBook().getBook_ISBN());
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error saving reservation: " + e.getMessage(), e);
        }
    }

    // Custom exception for data access issues
    public static class MyDataAccessException extends RuntimeException {
        public MyDataAccessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
