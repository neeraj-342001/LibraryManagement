package com.example.LibraryManagement.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LibraryManagement.dao.JdbcBookDAO;
import com.example.LibraryManagement.dao.JdbcMemberDAO;
import com.example.LibraryManagement.model.Book;
import com.example.LibraryManagement.model.Member;
import com.example.LibraryManagement.service.inter.MemberService;

@Service
public class MemberServiceImp implements MemberService {

    private final JdbcMemberDAO memberDAO;
    private final JdbcBookDAO bookDAO;

    @Autowired
    public MemberServiceImp(JdbcMemberDAO memberDAO, JdbcBookDAO bookDAO) {
        this.memberDAO = memberDAO;
        this.bookDAO = bookDAO;
    }


    @Override
    public void saveMember(Member member) {
        try {
            memberDAO.save(member);
        }
        catch (JdbcMemberDAO.MyDataAccessException e) {
            throw new MyServiceException("Error saving member: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Book> getBooksCheckedOutByMember(int memberID) {
        try {
            return bookDAO.getBooksCheckedOutByMember(memberID);
        }
        catch (JdbcBookDAO.MyDataAccessException e) {
            throw new MyServiceException("Error getting books checked out by member: " + e.getMessage(), e);
        }
    }

    // Custom exception for service layer issues
    public static class MyServiceException extends RuntimeException {
        public MyServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
