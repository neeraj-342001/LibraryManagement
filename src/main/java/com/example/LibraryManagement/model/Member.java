package com.example.LibraryManagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Member {
    @Id
    private int member_id;
    @NotNull(message = "username should not be null")
    private String name;
    @NotNull(message = "address should not be null")
    private String address;
    @Email(message = "Invalid Email address")
    private String mail;
}
