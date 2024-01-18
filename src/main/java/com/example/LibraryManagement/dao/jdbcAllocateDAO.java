package com.example.LibraryManagement.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.LibraryManagement.model.Allocate;

@Repository
public class jdbcAllocateDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Allocate> allocateRowMapper;

    public jdbcAllocateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.allocateRowMapper = new BeanPropertyRowMapper<>(Allocate.class);
    }

    public Integer countBooksForMember(int memberId) {
        String sql = "SELECT COUNT(*) FROM allocate WHERE member_id = ?";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, memberId);

        // Check for null before unboxing
        return (result != null) ? result : 0;
    }

    public int getSumOfDateDifferencesForMember(int memberId) {
        String sql = "SELECT SUM(DATEDIFF(end_date, CURRENT_DATE())) " +
                "FROM allocate " +
                "WHERE member_id = ? AND end_date > CURRENT_DATE()";

        Integer result = jdbcTemplate.queryForObject(sql, Integer.class, memberId);

        // Check for null before unboxing
        return (result != null) ? result : 0;
    }


    public void save(Allocate allocate) {
        String sql = "INSERT INTO allocate (member_id, book_ISBN, end_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, allocate.getMember().getMember_id(), allocate.getBook().getBook_ISBN(), allocate.getEnd_date());
    }

}
