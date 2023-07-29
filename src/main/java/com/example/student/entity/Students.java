package com.example.student.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Entity
@Table(name="Students")
public class Students {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    Integer rollNumber;
    String name;
    @DateTimeFormat(pattern="yyyy-mm-dd")
    LocalDate birthDate;
    String number;
    String userName;
    String passWord;
}
