package com.example.student.service;


import com.example.student.entity.Students;
import com.example.student.responce.PaginationRequestDto;
import com.example.student.responce.ResposeDTO;
import org.springframework.stereotype.Component;

@Component
public interface Service {
    ResposeDTO insertStudentData(Students students);

    ResposeDTO getAllData();

    ResposeDTO getAllDataPagination(PaginationRequestDto paginationRequestDto);
}
