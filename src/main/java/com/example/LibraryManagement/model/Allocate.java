package com.example.LibraryManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Allocate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int allocate_id;
    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "book_ISBN", referencedColumnName = "book_ISBN")
    private Book book;
    private LocalDate end_date;
}
