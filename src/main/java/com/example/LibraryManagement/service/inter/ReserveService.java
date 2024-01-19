package com.example.LibraryManagement.service.inter;

import java.util.List;

import com.example.LibraryManagement.model.Reserve;

public interface ReserveService {

    void saveReserve(Reserve reserve);

    List<Reserve> getReserveByMemberId(int memberId);
}
