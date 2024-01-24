package com.example.LibraryManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LibraryManagement.model.Book;
import com.example.LibraryManagement.model.Member;
import com.example.LibraryManagement.service.imp.MemberServiceImp;
import com.example.LibraryManagement.service.inter.MemberService;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveMember(@RequestBody Member member) {
        try {
            memberService.saveMember(member);
            return ResponseEntity.status(HttpStatus.CREATED).body("Member saved successfully");
        }
        catch (MemberServiceImp.MyServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving member");
        }
    }

    @GetMapping("/{memberId}/checked-out-books")
    public ResponseEntity<List<Book>> getBooksCheckedOutByMembers(@PathVariable int memberId) {
        try {
            List<Book> checkedOutBooks = memberService.getBooksCheckedOutByMember(memberId);
            return ResponseEntity.ok(checkedOutBooks);
        }
        catch (MemberServiceImp.MyServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
