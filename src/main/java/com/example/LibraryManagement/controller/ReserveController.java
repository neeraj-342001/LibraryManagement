package com.example.LibraryManagement.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.LibraryManagement.model.Reserve;
import com.example.LibraryManagement.service.inter.ReserveService;

@RestController
@RequestMapping("/api/reserve")
public class ReserveController {

    private final ReserveService reserveService;


    @Autowired
    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveReserve(@RequestBody Reserve reserve) {
        try {
            reserveService.saveReserve(reserve);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reserve saved successfully");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reserving member");
        }
    }

    public ResponseEntity<List<Reserve>> getReserveByMemberId(@PathVariable int memberId) {
        try {
            List<Reserve> reserveList = reserveService.getReserveByMemberId(memberId);
            return ResponseEntity.ok(reserveList);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
