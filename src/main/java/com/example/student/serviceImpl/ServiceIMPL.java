package com.example.student.serviceImpl;


import com.example.student.entity.Students;
import com.example.student.repository.StudentRepo;
import com.example.student.responce.ResposeDTO;
import com.example.student.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;


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
}
