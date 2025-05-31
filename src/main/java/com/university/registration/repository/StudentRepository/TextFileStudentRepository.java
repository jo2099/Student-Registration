package com.university.registration.repository.StudentRepository;

import com.university.registration.model.*;

import java.io.*;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.university.registration.model.Student;

public class TextFileStudentRepository implements StudentRepository {

  private String filePath = "students.txt";
  private int numMatricula = 0;

  public TextFileStudentRepository() {
    // Default constructor
    if (!new File(filePath).exists()) {
      try {
        new File(filePath).createNewFile(); // Create the file if it does not exist
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private String generateMatricula() {
    numMatricula++;
    String matricula = String.format("%08d", numMatricula);
    // Check if the matricula already exists
    while (getStudentByMatricula(matricula).isPresent()) {
      numMatricula++;
      matricula = String.format("%08d", numMatricula);
    }
    return matricula;

  }

  public TextFileStudentRepository(String filePath) {
    this.filePath = filePath;
    // Create the file if it does not exist
    if (!new File(filePath).exists()) {
      try {
        new File(filePath).createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  // Implement the methods defined in the interface
  @Override
  public void addStudent(Student student) {
    // Check if the student already exists
    // verifica se a matricula está presente
    if (student.getMatricula() == null || student.getMatricula().isEmpty()
        || getStudentByMatricula(student.getMatricula()).isPresent()) {
      // gera uma matricula nova
      student.setMatricula(generateMatricula());
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
      writer.write(student.getMatricula() + ',' + student.getName() + ',' + student.getGender() + ','
          + student.getDateTimeOfBirth().toString() + ','
          + student.getDataMatricula() + ',' + student.getNivelMatricula());
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Optional<Student> getStudentByMatricula(String matricula) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 6 && parts[0].equals(matricula)) {
          String name = parts[1];
          Genders gender = Genders.fromString(parts[2]);
          LocalDateTime dateTimeOfBirth = LocalDateTime.parse(parts[3]);
          LocalDateTime dataMatricula = LocalDateTime.parse(parts[4]);
          NivelMatricula nivelMatricula = NivelMatricula.valueOf(parts[5]);
          Student student = new Student(matricula, name, gender, dateTimeOfBirth, dataMatricula, nivelMatricula);
          student.setName(name);
          student.setGender(gender);
          student.setDateTimeOfBirth(dateTimeOfBirth);
          return Optional.of(student);

        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  @Override
  public void updateStudent(Student student) {
    try {
      File inputFile = new File(filePath);
      File tempFile = new File("temp.txt");

      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 6 && parts[0].equals(student.getMatricula())) {
          writer.write(student.getMatricula() + ',' + student.getName() + ',' + student.getGender() + ','
              + student.getDateTimeOfBirth().toString() + ','
              + student.getDataMatricula() + ',' + student.getNivelMatricula());
        } else {
          writer.write(line);
        }
        writer.newLine();
      }
      reader.close();
      writer.close();

      if (!inputFile.delete()) {
        System.out.println("Could not delete file");
        return;
      }
      if (!tempFile.renameTo(inputFile)) {
        System.out.println("Could not rename file");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteStudent(Student student) {
    try {
      File inputFile = new File(filePath);
      File tempFile = new File("temp.txt");

      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 6 && !parts[0].equals(student.getMatricula())) {
          writer.write(line);
          writer.newLine();
        }
      }
      reader.close();
      writer.close();

      if (!inputFile.delete()) {
        System.out.println("Could not delete file");
        return;
      }
      if (!tempFile.renameTo(inputFile)) {
        System.out.println("Could not rename file");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void deleteStudent(String matricula) {
    try {
      File inputFile = new File(filePath);
      File tempFile = new File("temp.txt");

      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 6 && !parts[0].equals(matricula)) {
          writer.write(line);
          writer.newLine();
        }
      }
      reader.close();
      writer.close();

      if (!inputFile.delete()) {
        System.out.println("Could not delete file");
        return;
      }
      if (!tempFile.renameTo(inputFile)) {
        System.out.println("Could not rename file");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Student> getAllStudents() {
    List<Student> students = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 6) {
          String matricula = parts[0];
          String name = parts[1];
          Genders gender = Genders.fromString(parts[2]);
          LocalDateTime dateTimeOfBirth = LocalDateTime.parse(parts[3]);
          LocalDateTime dataMatricula = LocalDateTime.parse(parts[4]);
          NivelMatricula nivelMatricula = NivelMatricula.valueOf(parts[5]);
          Student student = new Student(matricula, name, gender, dateTimeOfBirth, dataMatricula, nivelMatricula);
          student.setName(name);
          student.setGender(gender);
          student.setDateTimeOfBirth(dateTimeOfBirth);
          students.add(student);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return students;
  }
}
