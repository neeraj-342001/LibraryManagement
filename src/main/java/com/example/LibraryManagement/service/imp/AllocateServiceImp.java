package com.example.LibraryManagement.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LibraryManagement.dao.JdbcAllocateDAO;
import com.example.LibraryManagement.model.Allocate;
import com.example.LibraryManagement.service.inter.AllocateService;

@Service
public class AllocateServiceImp implements AllocateService {

    private final JdbcAllocateDAO allocateDAO;

    @Autowired
    public AllocateServiceImp(JdbcAllocateDAO allocateDAO) {
        this.allocateDAO = allocateDAO;
    }

    @Override
    public int calculateFine(int memberId, int fineFactor) {
        return (allocateDAO.getSumOfDateDifferencesForMember(memberId))*fineFactor;
    }

    @Override
    public List<Allocate> findOverdueAllocationsByMemberId(int memberId) {
        return allocateDAO.findOverdueAllocationsByMemberId(memberId);
    }

    @Override
    public void saveAllocate(Allocate allocate) {
        allocateDAO.save(allocate);
    }
}
