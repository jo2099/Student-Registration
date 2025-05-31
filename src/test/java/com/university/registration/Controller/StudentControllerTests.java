package com.university.registration.Controller;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.university.registration.controller.StudentController;
import com.university.registration.model.*;
import com.university.registration.repository.StudentRepository.StudentRepository;
import com.university.registration.repository.StudentRepository.TextFileStudentRepository;

class StudentControllerTests {

  @Mock
  private StudentRepository repository;

  private StudentController studentController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    studentController = new StudentController(repository);
  }

  @Test
  void testAddStudent() {
    Student student = new Student("12345678", "João Silva", Genders.HOMEM_CIS, LocalDateTime.of(2000, 1, 1, 0, 0),
        LocalDateTime.now(), NivelMatricula.GRADUACAO);

  }

}
