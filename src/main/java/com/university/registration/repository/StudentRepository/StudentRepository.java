package com.university.registration.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import com.university.registration.model.Student;

public interface StudentRepository {

  void addStudent(Student student);

  void updateStudent(Student student);

  void deleteStudent(Student student);

  List<Student> getAllStudents();

  Optional<Student> getStudentByMatricula(String matricula);
}
