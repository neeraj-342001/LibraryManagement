package com.example.LibraryManagement.service.inter;

import java.util.List;

import com.example.LibraryManagement.model.Allocate;

public interface AllocateService {

    int calculateFine(int memberId, int fineFactor);

    List<Allocate> findOverdueAllocationsByMemberId(int memberId);

    void saveAllocate(Allocate allocate);
}
