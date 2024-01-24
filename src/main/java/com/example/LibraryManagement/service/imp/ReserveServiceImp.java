package com.example.LibraryManagement.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.LibraryManagement.dao.JdbcReserveDAO;
import com.example.LibraryManagement.model.Reserve;
import com.example.LibraryManagement.service.inter.ReserveService;

@Service
public class ReserveServiceImp implements ReserveService {

    private final JdbcReserveDAO jdbcReserveDAO;

    @Autowired
    public ReserveServiceImp(JdbcReserveDAO jdbcReserveDAO) {
        this.jdbcReserveDAO = jdbcReserveDAO;
    }

    @Override
    public void saveReserve(Reserve reserve) {
        try {
            jdbcReserveDAO.save(reserve);
        }
        catch (JdbcReserveDAO.MyDataAccessException e) {
            throw new MyServiceException("Error saving reserve: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Reserve> getReserveByMemberId(int memberId) {
        try {
            return jdbcReserveDAO.getReserveByMemberId(memberId);
        }
        catch (JdbcReserveDAO.MyDataAccessException e) {
            throw new MyServiceException("Error getting reserves by member ID: " + e.getMessage(), e);
        }
    }

    // Custom exception for service layer issues
    public static class MyServiceException extends RuntimeException {
        public MyServiceException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
