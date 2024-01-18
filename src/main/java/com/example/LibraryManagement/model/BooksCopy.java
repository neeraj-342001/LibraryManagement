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

@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class BooksCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int book_id;
    @ManyToOne
    @JoinColumn(name = "book_ISBN", referencedColumnName = "book_ISBN")
    private Book book;
    private boolean occupied;
}
