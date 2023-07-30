package com.example.student.controller;


import com.example.student.entity.Students;
import com.example.student.responce.PaginationRequestDto;
import com.example.student.responce.ResposeDTO;
import com.example.student.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
*
*
* all student data controller
*
 */

@RestController
@RequestMapping("/suyash")
public class MyController {

    @Autowired
    Service service;
    @GetMapping("/app")
    public String check(){
        return "welcome to my app";
    }
    @PostMapping("/insertData")
    public ResposeDTO insertStudentData(@RequestBody Students students){
      return service.insertStudentData(students);
    }
    @GetMapping("/getAllData")
    public ResposeDTO getAllData(){
        return service.getAllData();
    }
    @PostMapping("/getAllDataPagination")
    public ResposeDTO getAllDataPagination(@RequestBody PaginationRequestDto paginationRequestDto){
        return service.getAllDataPagination(paginationRequestDto);
    }
}
