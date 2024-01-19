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
        jdbcReserveDAO.save(reserve);
    }

    @Override
    public List<Reserve> getReserveByMemberId(int memberId) {
        return jdbcReserveDAO.getReserveByMemberId(memberId);
    }
}
