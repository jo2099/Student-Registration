package com.university.registration.model;

import java.time.LocalDateTime;

public class Student extends Person {
  private String matricula = "00000000";
  private LocalDateTime data_matricula = LocalDateTime.now();
  private NivelMatricula nivelMatricula = NivelMatricula.INDEFINIDO;

  public Student(String matricula, LocalDateTime data_matricula, NivelMatricula nivelMatricula) {
    super();
    this.matricula = matricula;
    this.data_matricula = data_matricula;
    this.nivelMatricula = nivelMatricula;
  }

  public void setMatricula(String matricula) {
    if (matricula == null || !matricula.matches("\\d{8}")) {
      throw new IllegalArgumentException("Matricula deve conter exatamente 8 dígitos numéricos.");
    }
    this.matricula = matricula;
  }

  public LocalDateTime getDataMatricula() {
    return data_matricula;
  }

  public void setDataMatricula(LocalDateTime data_matricula) {
    if (data_matricula == null) {
      throw new IllegalArgumentException("Data de matrícula não pode ser nula.");
    } else if (data_matricula.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("Data de matrícula não pode ser no futuro.");
    } else if (data_matricula.isBefore(getDateTimeOfBirth())) {
      throw new IllegalArgumentException("Data de matrícula não pode ser anterior à data de nascimento.");
    }
    this.data_matricula = data_matricula;
  }

  public NivelMatricula getNivelMatricula() {
    return nivelMatricula;
  }

  public void setNivelMatricula(NivelMatricula nivelMatricula) {
    if (nivelMatricula == null) {
      throw new IllegalArgumentException("Nível de matrícula não pode ser nulo.");
    }
    this.nivelMatricula = nivelMatricula;
  }

  public String getMatricula() {
    return matricula;
  }
}
