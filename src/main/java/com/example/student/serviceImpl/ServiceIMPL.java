package com.example.student.serviceImpl;

import com.example.student.entity.Students;
import com.example.student.repository.StudentRepo;
import com.example.student.responce.PaginationRequestDto;
import com.example.student.responce.ResposeDTO;
import com.example.student.service.Service;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
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
                pageable=PageRequest.of(paginationRequestDto.getPageNumber()-1,paginationRequestDto.getPerPage(),
                        Sort.by(paginationRequestDto.getOrderBy()).ascending());
            }
        }else {
            if(paginationRequestDto.getPerPage()==-1){
                pageable=PageRequest.of(0,Integer.MAX_VALUE, Sort.by(paginationRequestDto.getOrderBy()).descending());
            }else {
                pageable=PageRequest.of(paginationRequestDto.getPageNumber()-1,paginationRequestDto.getPerPage(),
                        Sort.by(paginationRequestDto.getOrderBy()).descending());
            }
        } if(paginationRequestDto.getSearch().isSearchable()){

            Specification<Students> multiColoumnSort=null;
            if(paginationRequestDto.getOrderBy()!=null){
                multiColoumnSort=((root, query, criteriaBuilder) -> {
                    if("asc".equalsIgnoreCase(paginationRequestDto.getDir())){
                        query.orderBy(criteriaBuilder.asc(root.get(paginationRequestDto.getOrderBy())));
                    }else{
                        query.orderBy(criteriaBuilder.desc(root.get(paginationRequestDto.getOrderBy())));
                    }
                    return null;
                });
            }
            Specification<Students> specification=new Specification<Students>() {
                List<Predicate> predicateList=new ArrayList<>();

                @Override
                public Predicate toPredicate(Root<Students> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                    Predicate userName=null;
                    if(StringUtils.isNotBlank(paginationRequestDto.getSearch().getUserName())){
                        userName=criteriaBuilder.like((root.get("userName")),"%"+
                                paginationRequestDto.getSearch().getUserName()+"%");
                        predicateList.add(userName);
                    }
                    Predicate rollNumber= null;
                    if(StringUtils.isNotBlank(paginationRequestDto.getSearch().getRollNumber())){
                        rollNumber=criteriaBuilder.like(root.get("rollNumber").as(String.class),"%"+paginationRequestDto.getSearch().getRollNumber()+"%");
                        predicateList.add(rollNumber);
                    }

                    Predicate birthDate= null;
                    if(StringUtils.isNotBlank(paginationRequestDto.getSearch().getBirthDate())){
                        rollNumber=criteriaBuilder.like(root.get("birthDate").as(String.class),"%"+paginationRequestDto.getSearch().getBirthDate()+"%");
                        predicateList.add(birthDate);
                    }

                    return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
                }
            };
            specification.and(multiColoumnSort);
            studentsPage=studentRepo.findAll(specification,pageable);
            Map<String,Object> finalOutPut=new HashMap<>();
            finalOutPut.put("Current page",paginationRequestDto.getPageNumber());
            finalOutPut.put("total record",studentsPage.getTotalElements());
            finalOutPut.put("Student Data",studentsPage.getContent());
            resposeDTO.setData(finalOutPut);
            resposeDTO.setMessage("Sucess ");
            resposeDTO.setStatus(HttpStatus.OK.toString());
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
