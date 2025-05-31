package com.university.registration.utils;

public interface Subscriber<T> {

  /**
   * Receives an update with the given data.
   *
   * @param data the data sent by the publisher
   */
  void notify(T data);
}
