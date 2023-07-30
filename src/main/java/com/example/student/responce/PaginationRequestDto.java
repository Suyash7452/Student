package com.example.student.responce;

import lombok.Data;

@Data
public class PaginationRequestDto {
    private String orderBy;
    private String dir;
    private Integer perPage;
    private Integer pageNumber;
    private Search search;
    @Data
    public class Search{
        private boolean searchable;
        private String userName;
        private  String rollNumber;
        private String name;
        private String birthDate;
        private String number;

    }
}
