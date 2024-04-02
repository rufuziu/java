package com.example.repository;

import com.example.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
  List<Student> findByName(String name);
  List<Student> findByNameAndEmail(String name, String email);
  List<Student> findByNameOrEmail(String name, String email);
  /*
  List<Student> findByDepartmentDepartmentName(String deptname);
  List<Student> findBySubjectsSubjectName(String subname);
  */
  List<Student> findByEmailIsLike(String email);
  List<Student> findByNameStartsWith(String name);
  List<Student> findByDepartmentId(String deptId);

  //Native MongoDB Query in
  @Query ("{ \"name\": \"?0\" }")
  //?0 first parameter ?n
  List<Student> getByName(String name);
}

//Pagination
//
//Page No         Skip          Limit(Page Size)
//1               0             10
//2               10            10
//3               20            10
//
//(Page No - 1) * Page Size
