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

    public int calculateFine(int memberId, int fineFactor) {
        try {
            return (allocateDAO.getSumOfDateDifferencesForMember(memberId)) * fineFactor;
        }
        catch (JdbcAllocateDAO.MyDataAccessException e) {
            throw new MyServiceException("Error calculating fine: " + e.getMessage(), e);
        }
    }

    public List<Allocate> findOverdueAllocationsByMemberId(int memberId) {
        try {
            return allocateDAO.findOverdueAllocationsByMemberId(memberId);
        }
        catch (JdbcAllocateDAO.MyDataAccessException e) {
            throw new MyServiceException("Error finding overdue allocations: " + e.getMessage(), e);
        }
    }

    public void saveAllocate(Allocate allocate) {
        try {
            allocateDAO.save(allocate);
        }
        catch (JdbcAllocateDAO.MyDataAccessException e) {
            throw new MyServiceException("Error saving allocate: " + e.getMessage(), e);
        }
    }

    // Custom exception for service layer issues
    public static class MyServiceException extends RuntimeException {
        public MyServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
