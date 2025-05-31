package com.university.registration.model;

public class InvalidNameException extends Exception {
  private static final long serialVersionUID = 1L;

  public InvalidNameException(String message) {
    super(message);
  }

  public InvalidNameException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidNameException(Throwable cause) {
    super(cause);
  }

  public InvalidNameException() {
    super("Nome inválido fornecido.");
  }
}
