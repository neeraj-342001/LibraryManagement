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

import com.example.LibraryManagement.model.Allocate;
import com.example.LibraryManagement.service.imp.AllocateServiceImp;
import com.example.LibraryManagement.service.inter.AllocateService;

@RestController
@RequestMapping("/api/allocate")
public class AllocateController {

    private final AllocateService allocateService;


    @Autowired
    public AllocateController(AllocateService allocateService) {
        this.allocateService = allocateService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveAllocate(@RequestBody Allocate allocate) {
        try {
            allocateService.saveAllocate(allocate);
            return ResponseEntity.status(HttpStatus.CREATED).body("Allocate saved successfully");
        }
        catch (AllocateServiceImp.MyServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error allocating member");
        }
    }

    @GetMapping("/overdue/{memberId}")
    public ResponseEntity<List<Allocate>> getOverdueAllocationsByMemberId(@PathVariable int memberId) {
        try {
            List<Allocate> overdueAllocations = allocateService.findOverdueAllocationsByMemberId(memberId);
            return ResponseEntity.ok(overdueAllocations);
        }
        catch (AllocateServiceImp.MyServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/calculateFine/{memberId}/{fineFactor}")
    public ResponseEntity<Integer> calculateFine(@PathVariable int memberId, @PathVariable int fineFactor) {
        try {
            int fineAmount  = allocateService.calculateFine(memberId,fineFactor);
            return ResponseEntity.ok(fineAmount);
        }
        catch (AllocateServiceImp.MyServiceException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
