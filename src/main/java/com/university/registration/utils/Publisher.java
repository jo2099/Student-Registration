package com.university.registration.utils;

public interface Publisher<T> {

  /**
   * Registers a subscriber to receive updates.
   *
   * @param subscriber the subscriber to register
   */
  void register(Subscriber<T> subscriber);

  /**
   * Unregisters a subscriber from receiving updates.
   *
   * @param subscriber the subscriber to unregister
   */
  void unregister(Subscriber<T> subscriber);

  /**
   * Notifies all registered subscribers with the given data.
   *
   * @param data the data to send to subscribers
   */
  void notifySubscribers(T data);
}
