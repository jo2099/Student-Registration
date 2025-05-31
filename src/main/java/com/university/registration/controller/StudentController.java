package com.university.registration.controller;

import java.time.LocalDateTime;

import org.json.JSONObject;

import com.university.registration.model.Genders;
import com.university.registration.model.NivelMatricula;
import com.university.registration.model.Student;
import com.university.registration.repository.StudentRepository.StudentRepository;
import com.university.registration.utils.Event;
import com.university.registration.utils.Subscriber;
import com.university.registration.view.StudentView;

public class StudentController implements Subscriber<Event> {

  private StudentRepository studentRepository;
  private StudentView studentInterface;

  public StudentController(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
    this.studentInterface = null;
  }

  public void setStudentView(StudentView studentInterface) {
    this.studentInterface = studentInterface;
  }

  public void addStudent(Student student) {
    if (student == null) {
      throw new IllegalArgumentException("Student cannot be null");
    }
    if (student.getMatricula() != null) {
    }
    studentRepository.addStudent(student);
    showHomePage();
  }

  @Override
  public void notify(Event event) {
    if (event == null) {
      throw new IllegalArgumentException("Event cannot be null");
    } else if (event.getMessage() == "GET_STUDENTS") {
      // System.out.println("Received event: " + event.getMessage());
      // o evento é do tipo Event<List<Student>>
      event.setData(studentRepository.getAllStudents());
    } else if (event.getMessage() == "DELETE_STUDENT") {
      // System.out.println("Received event: " + event.getMessage());
      if (event.getData().isPresent()) {
        String matricula = (String) event.getData().get();
        studentRepository.deleteStudent(matricula);
        showHomePage();
      }
    } else if (event.getMessage() == "ERROR") {
      // System.out.println("Received event: " + event.getMessage());
      if (event.getData().isPresent()) {
        String message = (String) event.getData().get();
        showError(message);
      }
    } else if (event.getMessage() == "ADD_STUDENT_FORMS") {
      studentInterface.showRegistrationPage();
    } else if (event.getMessage() == "REGISTER_STUDENT") {
      // System.out.println("Received event: " + event.getMessage());
      if (event.getData().isPresent()) {
        JSONObject formData = (JSONObject) event.getData().get();
        try {
          Student student = new Student(
              formData.getString("nome"),
              Genders.fromString(formData.getString("genero")),
              LocalDateTime.parse(formData.getString("dataNascimento")),
              LocalDateTime.now(),
              NivelMatricula.fromString(formData.getString("nivelMatricula")));
          studentRepository.addStudent(student);
          studentInterface.showHomePage();
        } catch (Exception e) {
          showError("Invalid data: " + e.getMessage());
          return;
        }
      } else {
        showError("No data provided for student registration.");
      }
    } else {
      System.out.println("Unknown event: " + event.getMessage());
    }

  }

  public void showStudents() {
    if (studentInterface == null) {
      throw new IllegalStateException("studentInterface is not set");
    }

    studentInterface.showStudents(studentRepository.getAllStudents());

  }

  public void showError(String message) {
    if (studentInterface == null) {
      throw new IllegalStateException("studentInterface is not set");
    }

    studentInterface.showError(message);
  }

  public void showHomePage() {
    if (studentInterface == null) {
      throw new IllegalStateException("studentInterface is not set");
    }

    studentInterface.showHomePage();
  }

}
