package com.university.registration.model;

import java.time.LocalDateTime;

public class Staff extends Person {
  private LocalDateTime dataIngresso = LocalDateTime.now();
  private Cargo cargo = Cargo.INDEFINIDO;

  public Staff(String name, LocalDateTime dataIngresso, Cargo cargo) {
    super();
    setDataIngresso(dataIngresso);
    this.cargo = cargo;
  }

  public void setDataIngresso(LocalDateTime dataIngresso) {
    if (dataIngresso == null) {
      throw new IllegalArgumentException("Data de ingresso não pode ser nula.");
    } else if (dataIngresso.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("Data de ingresso não pode ser no futuro.");
    } else if (dataIngresso.isBefore(getDateTimeOfBirth())) {
      throw new IllegalArgumentException("Data de ingresso não pode ser anterior à data de nascimento.");
    }
  }

  public LocalDateTime getDataIngresso() {
    return dataIngresso;
  }

  public Cargo getCargo() {
    return cargo;
  }

  public void setCargo(Cargo cargo) {
    if (cargo == null) {
      throw new IllegalArgumentException("Cargo não pode ser nulo.");
    }
    this.cargo = cargo;
  }

}
