package com.university.registration.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import com.university.registration.model.Genders;
import com.university.registration.model.NivelMatricula;
import com.university.registration.model.Student;
import com.university.registration.utils.Event;
import com.university.registration.utils.Subscriber;

public class SwingInterface implements StudentView {

  private List<Subscriber<Event>> subscribers;
  private JFrame homePage;
  private JFrame registrationPage;
  private JTable studentTable;
  private DefaultTableModel tableModel;
  private JButton addButton;
  private JButton deleteButton;
  private JTextField nomeField;
  private JComboBox<String> generoComboBox;
  private JSpinner nascimentoSpinner;
  private JComboBox<String> nivelComboBox;
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  public SwingInterface() {
    this.subscribers = null;
    initHomePage();
    initRegistrationPage();
    addWindowCloseListener();
  }

  private void addWindowCloseListener() {
    homePage.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        listStudentsOnClose();
      }
    });
  }

  private void listStudentsOnClose() {
    Event<List<Student>> event = new Event<>("GET_STUDENTS");
    notifySubscribers(event);

    if (event.getData().isPresent()) {
      List<Student> students = event.getData().get();
      System.out.println("Lista de alunos cadastrados:");
      for (Student student : students) {
        System.out.println("Matrícula: " + student.getMatricula());
        System.out.println("Nome: " + student.getName());
        System.out.println("Gênero: " + student.getGender());
        System.out.println("Data de Nascimento: " + student.getDateTimeOfBirth());
        System.out.println("Data de Matrícula: " + student.getDataMatricula());
        System.out.println("Nível de Matrícula: " + student.getNivelMatricula());
        System.out.println("-----------------------------");
      }
    } else {
      System.out.println("Nenhum aluno encontrado.");
    }
  }

  private void initHomePage() {
    this.homePage = new JFrame("University Registration System");
    this.homePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.homePage.setSize(800, 600);

    // Create table for students
    String[] columnNames = { "Matricula", "Nome", "Gênero", "Data de Nascimento", "Data da Matricula",
        "Nível de Matrícula" };
    tableModel = new DefaultTableModel(columnNames, 0);
    studentTable = new JTable(tableModel);

    // Centralizar conteúdo das células
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
    for (int i = 0; i < studentTable.getColumnCount(); i++) {
      studentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }

    // Create buttons
    addButton = new JButton("Cadastrar Aluno");
    deleteButton = new JButton("Excluir Aluno");

    // Add components to the frame
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);

    homePage.setLayout(new BorderLayout());
    homePage.add(new JScrollPane(studentTable), BorderLayout.CENTER);
    homePage.add(buttonPanel, BorderLayout.SOUTH);

    // Add button listeners
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        notifySubscribers(new Event("ADD_STUDENT_FORMS"));
      }
    });

    deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
          String matricula = (String) tableModel.getValueAt(selectedRow, 0);
          Event<String> event = new Event<>("DELETE_STUDENT");
          event.setData(matricula);
          notifySubscribers(event);
        } else {
          Event<String> event = new Event<>("ERROR");
          event.setData("Nenhum aluno selecionado para exclusão.");
          notifySubscribers(event);
        }
      }
    });
  }

  private void initRegistrationPage() {
    this.registrationPage = new JFrame("Register Student");
    this.registrationPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.registrationPage.setSize(400, 300);

    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

    // Nome field
    JPanel nomePanel = new JPanel();
    nomePanel.add(new javax.swing.JLabel("Nome:"));
    nomeField = new JTextField(20);
    nomePanel.add(nomeField);
    formPanel.add(nomePanel);

    // Genero field
    JPanel generoPanel = new JPanel();
    generoPanel.add(new javax.swing.JLabel("Genero:"));
    generoComboBox = new JComboBox<>(Genders.getAllValues());
    generoPanel.add(generoComboBox);
    formPanel.add(generoPanel);

    // Data de nascimento field (using JSpinner)
    JPanel nascimentoPanel = new JPanel();
    nascimentoPanel.add(new javax.swing.JLabel("Data de Nascimento:"));
    nascimentoSpinner = new JSpinner(new SpinnerDateModel());
    JSpinner.DateEditor editor = new JSpinner.DateEditor(nascimentoSpinner, "dd/MM/yyyy");
    nascimentoSpinner.setEditor(editor);
    nascimentoPanel.add(nascimentoSpinner);
    formPanel.add(nascimentoPanel);

    // Nivel Matricula field
    JPanel nivelPanel = new JPanel();
    nivelPanel.add(new javax.swing.JLabel("Nivel Matricula:"));
    nivelComboBox = new JComboBox<>(NivelMatricula.getAllValues());
    nivelPanel.add(nivelComboBox);
    formPanel.add(nivelPanel);

    // Submit button
    JButton submitButton = new JButton("Registrar");
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String nome = nomeField.getText();
        String genero = (String) generoComboBox.getSelectedItem();
        Date nascimentoDate = (Date) nascimentoSpinner.getValue();
        String nivel = (String) nivelComboBox.getSelectedItem();

        LocalDateTime nascimento = LocalDateTime.ofInstant(nascimentoDate.toInstant(),
            java.time.ZoneId.systemDefault());

        JSONObject registrationData = new JSONObject();
        registrationData.put("nome", nome);
        registrationData.put("genero", genero);
        registrationData.put("dataNascimento", nascimento.toString());
        registrationData.put("nivelMatricula", nivel);

        Event<JSONObject> event = new Event<>("REGISTER_STUDENT");
        event.setData(registrationData);
        notifySubscribers(event);
      }
    });
    formPanel.add(submitButton);

    registrationPage.add(formPanel);
  }

  private void clearRegistrationForm() {
    nomeField.setText("");
    generoComboBox.setSelectedIndex(0);
    nascimentoSpinner.setValue(new Date());
    nivelComboBox.setSelectedIndex(0);
  }

  @Override
  public void register(Subscriber<Event> subscriber) {
    if (subscriber == null) {
      throw new IllegalArgumentException("Subscriber cannot be null");
    }
    if (subscribers == null) {
      subscribers = new java.util.ArrayList<>();
    }
    subscribers.add(subscriber);
  }

  @Override
  public void unregister(Subscriber<Event> subscriber) {
    if (subscriber == null) {
      throw new IllegalArgumentException("Subscriber cannot be null");
    }
    if (subscribers != null) {
      subscribers.remove(subscriber);
    }
  }

  @Override
  public void notifySubscribers(Event data) {
    if (subscribers == null || subscribers.isEmpty()) {
      return;
    }
    for (Subscriber<Event> subscriber : subscribers) {
      subscriber.notify(data);
    }
  }

  @Override
  public void showStudents(List<Student> students) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'showStudents'");
  }

  @Override
  public void showError(String message) {
    if (message == null || message.isEmpty()) {
      throw new IllegalArgumentException("Message cannot be null or empty");
    }
    // Display error message in a dialog or console
    System.err.println("Error: " + message);
    // dialog box
    javax.swing.JOptionPane.showMessageDialog(homePage, message, "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void showHomePage() {
    if (homePage == null) {
      throw new IllegalStateException("Home page is not initialized");
    }
    if (registrationPage != null) {
      registrationPage.dispose();
    }
    // notifica os subscribers para receber os students
    Event<List<Student>> event = new Event<>("GET_STUDENTS");
    notifySubscribers(event);

    if (event.getData().isPresent()) {
      List<Student> students = event.getData().get();
      tableModel.setRowCount(0); // Clear existing rows
      for (Student student : students) {
        tableModel.addRow(new Object[] {
            student.getMatricula(),
            student.getName(),
            student.getGender().toString(),
            student.getDateTimeOfBirth().toLocalDate().format(DATE_FORMATTER),
            student.getDataMatricula().toLocalDate().format(DATE_FORMATTER),
            student.getNivelMatricula()
        });
      }
    } else {
      showError("Nenhum aluno encontrado.");
    }

    homePage.setVisible(true);
  }

  @Override
  public void showRegistrationPage() {
    if (registrationPage == null) {
      throw new IllegalStateException("Registration page is not initialized");
    }
    clearRegistrationForm(); // Limpa os campos do formulário
    registrationPage.setVisible(true);
  }

}
