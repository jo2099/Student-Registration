package com.university.registration.view;

import com.university.registration.model.Student;
import com.university.registration.utils.Event;
import com.university.registration.utils.Publisher;

import java.util.List;

public interface StudentView extends Publisher<Event> {

  void showStudents(List<Student> students);

  void showError(String message);

  void showHomePage();

  void showRegistrationPage();

}
