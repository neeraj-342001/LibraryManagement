package com.example.LibraryManagement.service.inter;

import java.util.List;

import com.example.LibraryManagement.model.Book;
import com.example.LibraryManagement.model.Member;

public interface MemberService {

    void saveMember(Member member);

    List<Book> getBooksCheckedOutByMember(int memberID);
}
