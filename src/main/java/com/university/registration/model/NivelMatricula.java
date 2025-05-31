package com.university.registration.model;

public enum NivelMatricula {
  INDEFINIDO,
  GRADUACAO,
  ESPECIALIZACAO,
  MESTRADO,
  DOUTORADO;

  public static String[] getAllValues() {
    return new String[] {
        INDEFINIDO.name(),
        GRADUACAO.name(),
        ESPECIALIZACAO.name(),
        MESTRADO.name(),
        DOUTORADO.name()
    };
  }

  public static NivelMatricula fromString(String nivel) {
    for (NivelMatricula n : NivelMatricula.values()) {
      if (n.name().equalsIgnoreCase(nivel)) {
        return n;
      }
    }
    throw new IllegalArgumentException("Invalid nivel: " + nivel);
  }
}
