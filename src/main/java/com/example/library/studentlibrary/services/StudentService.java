package com.example.library.studentlibrary.services;

import com.example.library.studentlibrary.models.CardStatus;
import com.example.library.studentlibrary.models.Student;
import com.example.library.studentlibrary.repositories.CardRepository;
import com.example.library.studentlibrary.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {


    @Autowired
    CardService cardService4;

    @Autowired
    StudentRepository studentRepository4;
    @Autowired
    CardRepository cardrepository;

    public Student getDetailsByEmail(String email){
        Student student = studentRepository4.findByEmailId(email);

        return student;
    }

    public Optional<Student> getDetailsById(int id){
        Optional<Student> student = studentRepository4.findById(id);

        return student;
    }

    public void createStudent(Student student){
        studentRepository4.save(student);

    }

    public void updateStudent(Student student){
 studentRepository4.updateStudentDetails(student);

    }

    public void deleteStudent(int id){
        //Delete student and deactivate corresponding card
studentRepository4.deleteCustom(id);
cardrepository.deactivateCard(id,  CardStatus.DEACTIVATED.toString());

    }
}