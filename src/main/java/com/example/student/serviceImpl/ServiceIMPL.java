package com.example.student.serviceImpl;


import com.example.student.entity.Students;
import com.example.student.repository.StudentRepo;
import com.example.student.responce.PaginationRequestDto;
import com.example.student.responce.ResposeDTO;
import com.example.student.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@org.springframework.stereotype.Service
public class ServiceIMPL implements Service {

    @Autowired
    StudentRepo studentRepo;
    @Override
    public ResposeDTO insertStudentData(Students students) {
        ResposeDTO resposeDTO=new ResposeDTO();
       Students  students1= studentRepo.save(students);
        resposeDTO.setData(students1);
        resposeDTO.setStatus(HttpStatus.OK.toString());
        resposeDTO.setMessage("sucessfully insert data into student table");
        return resposeDTO;
    }

    @Override
    public ResposeDTO getAllData() {
        ResposeDTO resposeDTO=new ResposeDTO();
        List<Students> studentsList=studentRepo.findAll();
        resposeDTO.setData(studentsList);
        resposeDTO.setStatus(HttpStatus.OK.toString());
        resposeDTO.setMessage("sucessfully fatch data from student table");
        return resposeDTO;
    }


    @Override
    public ResposeDTO getAllDataPagination(PaginationRequestDto paginationRequestDto) {
        ResposeDTO resposeDTO=new ResposeDTO();
        Page<Students> studentsPage=null;
        Pageable pageable=null;

        if(paginationRequestDto.getDir().equalsIgnoreCase("asc")){
            if(paginationRequestDto.getPerPage()==-1){
                pageable=PageRequest.of(0,Integer.MAX_VALUE, Sort.by(paginationRequestDto.getOrderBy()).ascending());
            }else {
                pageable=PageRequest.of(paginationRequestDto.getPageNumber(),paginationRequestDto.getPerPage(),
                        Sort.by(paginationRequestDto.getOrderBy()).ascending());
            }
        }else {
            if(paginationRequestDto.getPerPage()==-1){
                pageable=PageRequest.of(0,Integer.MAX_VALUE, Sort.by(paginationRequestDto.getOrderBy()).descending());
            }else {
                pageable=PageRequest.of(paginationRequestDto.getPageNumber(),paginationRequestDto.getPerPage(),
                        Sort.by(paginationRequestDto.getOrderBy()).descending());
            }
        } if(paginationRequestDto.getSearch().isSearchable()){

        }else {
            studentsPage=studentRepo.findAll(pageable);
            Map<String,Object> finalOutPut=new HashMap<>();
            finalOutPut.put("Current page",paginationRequestDto.getPageNumber());
            finalOutPut.put("total record",studentsPage.getTotalElements());
            finalOutPut.put("Student Data",studentsPage.getContent());
            resposeDTO.setData(finalOutPut);
            resposeDTO.setMessage("Sucess ");
            resposeDTO.setStatus(HttpStatus.OK.toString());

        }
        return resposeDTO;
    }

}
