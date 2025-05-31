package com.university.registration;

import java.time.LocalDateTime;

import com.university.registration.controller.StudentController;
import com.university.registration.model.NivelMatricula;
import com.university.registration.model.Student;
import com.university.registration.repository.StudentRepository.TextFileStudentRepository;
import com.university.registration.view.*;

public class App {
  public static void main(String[] args) {
    TextFileStudentRepository textFileStudentRepository = new TextFileStudentRepository();
    StudentController studentController = new StudentController(textFileStudentRepository);
    StudentView studentView = new SwingInterface();
    studentView.register(studentController);
    studentController.setStudentView(studentView);

    studentController.showHomePage();

  }
}
