package com.example.LibraryManagement.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.LibraryManagement.model.Member;

@Repository
public class JdbcMemberDAO {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Member> memberRowMapper;

    public JdbcMemberDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.memberRowMapper = new BeanPropertyRowMapper<>(Member.class);
    }

    public List<Member> getMembersWhoTookBook(int bookISBN) {
        String sql = "SELECT m.* FROM member m " +
                "JOIN allocate a ON m.member_id = a.member_id " +
                "WHERE a.book_ISBN = ?";
        return jdbcTemplate.query(sql, memberRowMapper, bookISBN);
    }

    public List<Member> getMembersWhoReservedBook(int bookISBN) {
        String sql = "SELECT m.* FROM member m " +
                "JOIN reserve r ON m.member_id = r.member_id " +
                "WHERE r.book_ISBN = ?";
        return jdbcTemplate.query(sql, memberRowMapper, bookISBN);
    }

    public Member findById(int memberId) {
        String sql = "SELECT * FROM member WHERE member_id = ?";
        return jdbcTemplate.queryForObject(sql, memberRowMapper, memberId);
    }

    public void save(Member member) {
        String sql = "INSERT INTO member (member_id, name, address, mail) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, member.getMember_id(), member.getName(), member.getAddress(), member.getMail());
    }
}
