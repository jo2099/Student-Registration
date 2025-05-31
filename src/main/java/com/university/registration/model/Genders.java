package com.university.registration.model;

public enum Genders {
  INDEFINIDO,
  HOMEM_CIS,
  MULHER_CIS,
  NAO_BINARIO,
  TRANSGENERO;

  // Cast string to Genders
  public static Genders fromString(String gender) {
    for (Genders g : Genders.values()) {
      if (g.name().equalsIgnoreCase(gender)) {
        return g;
      }
    }
    throw new IllegalArgumentException("Invalid gender: " + gender);
  }
}
