package com.university.registration.model;

import java.time.LocalDateTime;

abstract class Person {
  private String name = "Nome Indefinido";
  private LocalDateTime dateTimeOfBirth = LocalDateTime.now();
  private Genders gender = Genders.INDEFINIDO;

  public Person(String name, LocalDateTime dateTimeOfBirth, Genders gender) throws InvalidNameException {
    setName(name);
    setDateTimeOfBirth(dateTimeOfBirth);
    setGender(gender);
  }

  public Person() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Nome não pode ser nulo ou vazio.");
    }
    if (!name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)+$")) {
      throw new IllegalArgumentException("Nome deve ter o formato 'Nome Sobrenome' e não pode conter números.");
    }
    this.name = name;
  }

  public LocalDateTime getDateTimeOfBirth() {
    return dateTimeOfBirth;
  }

  public void setDateTimeOfBirth(LocalDateTime dateTimeOfBirth) {
    if (dateTimeOfBirth == null) {
      throw new IllegalArgumentException("Data de nascimento não pode ser nula.");
    } else if (dateTimeOfBirth.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("Data de nascimento não pode ser no futuro.");
    }
    this.dateTimeOfBirth = dateTimeOfBirth;
  }

  public Genders getGender() {
    return this.gender;
  }

  public void setGender(Genders gender) {
    this.gender = gender;
  }

}
