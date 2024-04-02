package com.example.service;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class StudentService {
  @Autowired
  StudentRepository studentRepository;

  public Student createStudent(Student student) {
    return studentRepository.save(student);
  }
  public Student getStudentById(String id) {
    return studentRepository.findById(id).get();
  }
  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }
  public Student updateStudent(Student student) {
    return studentRepository.save(student);
  }
  public String deleteStudent(String id) {
    studentRepository.deleteById(id);
    return "Student has been deleted!";
  }
  public List<Student> getStudentsByName(String name) {
    return studentRepository.findByName(name);
  }
  public List<Student> getStudentsByNameAndEmail(String name, String mail) {
    return studentRepository.findByNameAndEmail(name,mail);
  }
  public List<Student> getStudentsByNameOrEmail(String name, String email) {
    return studentRepository.findByNameOrEmail(name,email);
  }
  public List<Student> getAllWithPagination(int pageNo, int pageSize ){
    PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
    return studentRepository.findAll(pageable).getContent();
  }
  public List<Student> allWithSorting(){
    Sort sort = Sort.by(Sort.Direction.ASC, "name");
    return studentRepository.findAll(sort);
  }
  /*
  public List<Student> byDepartmentName(String deptName){
    return studentRepository.findByDepartmentDepartmentName(deptName);
  }
  public List<Student> bySubjectName(String subName){
    return studentRepository.findBySubjectsSubjectName(subName);
  }
  */

  public List<Student> emailLike(String email){
    return studentRepository.findByEmailIsLike(email);
  }
  public List<Student> nameStartsWith(String name){
    return studentRepository.findByNameStartsWith(name);
  }
  public List<Student> byDepartmentId(String deptId){
    return studentRepository.findByDepartmentId(deptId);
  }
}
