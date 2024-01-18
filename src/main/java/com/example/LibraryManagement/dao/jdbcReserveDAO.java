package com.example.LibraryManagement.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.LibraryManagement.model.Reserve;

@Repository
public class jdbcReserveDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Reserve> reserveRowMapper;

    public jdbcReserveDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.reserveRowMapper = new BeanPropertyRowMapper<>(Reserve.class);
    }

    public void save(Reserve reserve) {
        String sql = "INSERT INTO reserve (member_id, book_ISBN) VALUES (?, ?)";
        jdbcTemplate.update(sql, reserve.getMember().getMember_id(), reserve.getBook().getBook_ISBN());
    }
}
