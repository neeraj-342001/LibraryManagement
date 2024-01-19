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
        memberDAO.save(member);
    }

    @Override
    public List<Book> getBooksCheckedOutByMember(int memberID) {
        return bookDAO.getBooksCheckedOutByMember(memberID);
    }
}
