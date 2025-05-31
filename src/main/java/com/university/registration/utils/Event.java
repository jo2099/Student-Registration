package com.university.registration.utils;

import java.util.Optional;

public class Event<T> {

  private String type;
  private String message;
  private T data;

  public Event(String message) {
    this.type = null;
    this.message = message;
  }

  public Optional<T> getData() {
    if (data == null) {
      return Optional.empty();
    }
    return Optional.of(data);
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
