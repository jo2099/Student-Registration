package com.university.registration.Repository.StudentRepository;

import com.university.registration.model.*;
import com.university.registration.repository.StudentRepository.TextFileStudentRepository;

import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TextFileStudentRepositoryTests {

  private TextFileStudentRepository repository;
  private File tempFile;

  @BeforeEach
  void setUp() throws IOException {
    // Criar um arquivo temporário para os testes
    tempFile = File.createTempFile("students", ".txt");
    repository = new TextFileStudentRepository();
    repository.setFilePath(tempFile.getAbsolutePath());
  }

  @AfterEach
  void tearDown() {
    // Deletar o arquivo temporário após os testes
    if (tempFile.exists()) {
      tempFile.delete();
    }
  }

  @Test
  void testAddStudent() {
    Student student = new Student("12345678", LocalDateTime.now(), NivelMatricula.GRADUACAO);
    student.setName("João Silva");
    student.setGender(Genders.HOMEM_CIS);
    student.setDateTimeOfBirth(LocalDateTime.of(2000, 1, 1, 0, 0));

    repository.addStudent(student);

    Optional<Student> retrievedStudent = repository.getStudentByMatricula("12345678");
    assertTrue(retrievedStudent.isPresent());
    assertEquals("João Silva", retrievedStudent.get().getName());
  }

  @Test
  void testAddExistingStudent() {
    Student student = new Student("12345678", LocalDateTime.now(), NivelMatricula.GRADUACAO);
    student.setName("João Silva");
    student.setGender(Genders.HOMEM_CIS);
    student.setDateTimeOfBirth(LocalDateTime.of(2000, 1, 1, 0, 0));
    repository.addStudent(student);
    // Tentar adicionar o mesmo aluno novamente
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      repository.addStudent(student);
    });

    // Verificar a mensagem da exceção
    assertEquals("Aluno já existe no repositório.", exception.getMessage());

  }

  @Test
  void testGetStudentByMatricula() {
    Student student = new Student("87654321", LocalDateTime.now(), NivelMatricula.MESTRADO);
    student.setName("Maria Oliveira");
    student.setGender(Genders.MULHER_CIS);
    student.setDateTimeOfBirth(LocalDateTime.of(1995, 5, 15, 0, 0));

    repository.addStudent(student);

    Optional<Student> retrievedStudent = repository.getStudentByMatricula("87654321");
    assertTrue(retrievedStudent.isPresent());
    assertEquals("Maria Oliveira", retrievedStudent.get().getName());
  }

  @Test
  void testUpdateStudent() {
    Student student = new Student("12345678", LocalDateTime.now(), NivelMatricula.GRADUACAO);
    student.setName("João Silva");
    student.setGender(Genders.HOMEM_CIS);
    student.setDateTimeOfBirth(LocalDateTime.of(2000, 1, 1, 0, 0));

    repository.addStudent(student);

    student.setName("João Pedro Silva");
    repository.updateStudent(student);

    Optional<Student> updatedStudent = repository.getStudentByMatricula("12345678");
    assertTrue(updatedStudent.isPresent());
    assertEquals("João Pedro Silva", updatedStudent.get().getName());
  }

  @Test
  void testDeleteStudent() {
    Student student = new Student("12345678", LocalDateTime.now(), NivelMatricula.GRADUACAO);
    student.setName("João Silva");
    student.setGender(Genders.HOMEM_CIS);
    student.setDateTimeOfBirth(LocalDateTime.of(2000, 1, 1, 0, 0));

    repository.addStudent(student);
    repository.deleteStudent(student);

    Optional<Student> deletedStudent = repository.getStudentByMatricula("12345678");
    assertFalse(deletedStudent.isPresent());
  }

  @Test
  void testGetAllStudents() {
    Student student1 = new Student("12345678", LocalDateTime.now(), NivelMatricula.GRADUACAO);
    student1.setName("João Silva");
    student1.setGender(Genders.HOMEM_CIS);
    student1.setDateTimeOfBirth(LocalDateTime.of(2000, 1, 1, 0, 0));

    Student student2 = new Student("87654321", LocalDateTime.now(), NivelMatricula.MESTRADO);
    student2.setName("Maria Oliveira");
    student2.setGender(Genders.MULHER_CIS);
    student2.setDateTimeOfBirth(LocalDateTime.of(1995, 5, 15, 0, 0));

    repository.addStudent(student1);
    repository.addStudent(student2);

    List<Student> students = repository.getAllStudents();
    assertEquals(2, students.size());
  }
}
