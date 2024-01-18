package com.example.LibraryManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Book {
    @Id
    private int book_ISBN;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Message cannot be blank")
    private String author;
    @NotBlank(message = "Category cannot be blank")
    private String category;
    @NotNull(message = "Publication date cannot be null")
    @PastOrPresent(message = "Publication date must be in Past otr Present")
    private String publication_date;
    @PositiveOrZero(message = "Rack Number should be positive or zero")
    private int rack_number;
    @PositiveOrZero(message = "Book count must be positive or zero")
    private int count;
}
