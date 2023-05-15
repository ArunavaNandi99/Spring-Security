package com.arunava.springsecurity.controller;

import com.arunava.springsecurity.model.Student;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Arunava Nandi"),
            new Student(2 , "Tom Crue"),
            new Student( 3 , "Sayanta Nandi")
    );

    @GetMapping("/")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
    public List<Student> getStudent(){
        return STUDENTS;
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('student:write')")
    public String post(){
        return  "post Request";
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('student:write')")
    public String update(){
        return  "update Request";
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAuthority('student:write')")
    public String delete(){
        return "delete Request";
    }
}
