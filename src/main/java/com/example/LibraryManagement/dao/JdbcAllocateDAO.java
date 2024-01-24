package com.example.LibraryManagement.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.LibraryManagement.model.Allocate;

@Repository
public class JdbcAllocateDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Allocate> allocateRowMapper;

    public JdbcAllocateDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.allocateRowMapper = new BeanPropertyRowMapper<>(Allocate.class);
    }

    public Integer countBooksForMember(int memberId) {
        try {
            String sql = "SELECT COUNT(*) FROM allocate WHERE member_id = ?";
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, memberId);

            // Check for null before unboxing
            return (result != null) ? result : 0;
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error counting books for member: " + e.getMessage(), e);
        }
    }

    public int getSumOfDateDifferencesForMember(int memberId) {
        try {
            String sql = "SELECT SUM(DATEDIFF(end_date, CURRENT_DATE())) " +
                    "FROM allocate " +
                    "WHERE member_id = ? AND end_date > CURRENT_DATE()";

            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, memberId);

            // Check for null before unboxing
            return (result != null) ? result : 0;
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error calculating sum of date differences: " + e.getMessage(), e);
        }
    }

    public List<Allocate> findOverdueAllocationsByMemberId(int memberId) {
        try {
            String sql = "SELECT * FROM allocate WHERE member_id = ? AND end_date < CURRENT_DATE";
            return jdbcTemplate.query(sql, allocateRowMapper, memberId);
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error retrieving overdue allocations: " + e.getMessage(), e);
        }
    }

    public boolean existsByMemberIdAndBookISBN(int memberId, int bookISBN) {
        try {
            String sql = "SELECT COUNT(*) FROM allocate WHERE member_id = ? AND book_ISBN = ?";
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class, memberId, bookISBN);
            int count = (result != null) ? result : 0;
            return count > 0;
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error checking existence by member ID and book ISBN: " + e.getMessage(), e);
        }
    }

    public void deleteByMemberIdAndBookISBN(int memberId, int bookISBN) {
        try {
            String sql = "DELETE FROM allocate WHERE member_id = ? AND book_ISBN = ?";
            jdbcTemplate.update(sql, memberId, bookISBN);
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error deleting by member ID and book ISBN: " + e.getMessage(), e);
        }
    }

    public void save(Allocate allocate) {
        try {
            String sql = "INSERT INTO allocate (member_id, book_ISBN, end_date) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, allocate.getMember().getMember_id(), allocate.getBook().getBook_ISBN(), allocate.getEnd_date());
        }
        catch (DataAccessException e) {
            throw new MyDataAccessException("Error saving allocation: " + e.getMessage(), e);
        }
    }

    public static class MyDataAccessException extends RuntimeException {
        public MyDataAccessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
