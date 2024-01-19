package com.example.LibraryManagement.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        String sql = "SELECT COUNT(*) FROM reserve WHERE member_id = ? AND book_ISBN = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, memberId, bookISBN);
        return (count != null && count > 0);
    }

    public Reserve getFirstReservation(int bookISBN) {
        String sql = "SELECT * FROM reserve WHERE book_ISBN = ? ORDER BY reverse_id LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, reserveRowMapper, bookISBN);
        } catch (EmptyResultDataAccessException e) {
            return null; // No reservation found for the specified book
        }
    }

    public boolean existsByBookISBN(int bookISBN) {
        String sql = "SELECT COUNT(*) FROM reserve WHERE book_ISBN = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, bookISBN);
        return (count != null && count > 0);
    }

    public void deleteByMemberIdAndBookISBN(int memberId, int bookISBN) {
        String sql = "DELETE FROM reserve WHERE member_id = ? AND book_ISBN = ?";
        jdbcTemplate.update(sql, memberId, bookISBN);
    }

    public List<Reserve> getReserveByMemberId(int memberId) {
        String sql = "SELECT * FROM reserve WHERE member_id = ?";
        return jdbcTemplate.query(sql, reserveRowMapper, memberId);
    }

    public void save(Reserve reserve) {
        String sql = "INSERT INTO reserve (member_id, book_ISBN) VALUES (?, ?)";
        jdbcTemplate.update(sql, reserve.getMember().getMember_id(), reserve.getBook().getBook_ISBN());
    }
}
