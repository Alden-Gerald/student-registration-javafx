package application;

import java.sql.SQLException;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final TextField studentIdField = new TextField();
    private final TextField nameField = new TextField();
    private final ComboBox<String> majorComboBox = new ComboBox<>();
    private final TextField emailField = new TextField();
    private final TextField phoneField = new TextField();

    private final TableView<Student> studentTable = new TableView<>();
    private final Label statusLabel = new Label("No student selected");

    private final StudentDAO studentDAO = new StudentDAO();

    private String originalStudentId;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        root.setCenter(createContent());
        root.setBottom(createStatusBar());

        configureTableEvents();
        configurePhoneEvent();
        loadData();

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("New Student Registration System");
        stage.setMinWidth(800);
        stage.setMinHeight(550);
        stage.setScene(scene);

        stage.setOnCloseRequest(event -> {
            event.consume();
            confirmExit();
        });

        stage.show();
    }

    private VBox createContent() {
        Label applicationTitle = new Label(
                "NEW STUDENT REGISTRATION SYSTEM"
        );

        Label formTitle = new Label(
                "Student Registration Form"
        );

        GridPane form = createForm();
        HBox buttons = createButtons();

        Label tableTitle = new Label(
                "New Student Data"
        );

        createTableColumns();

        VBox.setVgrow(studentTable, Priority.ALWAYS);

        VBox content = new VBox(
                12,
                applicationTitle,
                formTitle,
                form,
                buttons,
                tableTitle,
                studentTable
        );

        content.setPadding(new Insets(15));

        return content;
    }

    private GridPane createForm() {
        studentIdField.setPromptText("Student ID");
        nameField.setPromptText("Full name");

        majorComboBox.getItems().addAll(
                "Information Systems",
                "Computer Science",
                "Business Information Technology",
                "Data Science",
                "Cyber Security"
        );

        majorComboBox.setPromptText("Select major");
        majorComboBox.setMaxWidth(Double.MAX_VALUE);

        emailField.setPromptText("name@email.com");
        phoneField.setPromptText("Digits only");

        GridPane form = new GridPane();

        form.setHgap(10);
        form.setVgap(10);

        form.add(new Label("Student ID:"), 0, 0);
        form.add(studentIdField, 1, 0);

        form.add(new Label("Name:"), 0, 1);
        form.add(nameField, 1, 1);

        form.add(new Label("Major:"), 0, 2);
        form.add(majorComboBox, 1, 2);

        form.add(new Label("Email:"), 0, 3);
        form.add(emailField, 1, 3);

        form.add(new Label("Phone:"), 0, 4);
        form.add(phoneField, 1, 4);

        GridPane.setHgrow(
                studentIdField,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                nameField,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                majorComboBox,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                emailField,
                Priority.ALWAYS
        );

        GridPane.setHgrow(
                phoneField,
                Priority.ALWAYS
        );

        return form;
    }

    private HBox createButtons() {
        Button addButton = new Button("Add Student");
        Button updateButton = new Button("Update Student");
        Button clearButton = new Button("Clear");
        Button refreshButton = new Button("Refresh");
        Button exitButton = new Button("Exit");

        addButton.setDefaultButton(true);

        addButton.setOnAction(
                event -> addStudent()
        );

        updateButton.setOnAction(
                event -> updateStudent()
        );

        clearButton.setOnAction(
                event -> clearForm()
        );

        refreshButton.setOnAction(
                event -> loadData()
        );

        exitButton.setOnAction(
                event -> confirmExit()
        );

        HBox buttons = new HBox(
                10,
                addButton,
                updateButton,
                clearButton,
                refreshButton,
                exitButton
        );

        buttons.setAlignment(Pos.CENTER_LEFT);

        return buttons;
    }

    private void createTableColumns() {
        TableColumn<Student, String> idColumn =
                new TableColumn<>("Student ID");

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("studentId")
        );

        TableColumn<Student, String> nameColumn =
                new TableColumn<>("Name");

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<>("name")
        );

        TableColumn<Student, String> majorColumn =
                new TableColumn<>("Major");

        majorColumn.setCellValueFactory(
                new PropertyValueFactory<>("major")
        );

        TableColumn<Student, String> emailColumn =
                new TableColumn<>("Email");

        emailColumn.setCellValueFactory(
                new PropertyValueFactory<>("email")
        );

        TableColumn<Student, String> phoneColumn =
                new TableColumn<>("Phone");

        phoneColumn.setCellValueFactory(
                new PropertyValueFactory<>("phone")
        );

        studentTable.getColumns().add(idColumn);
        studentTable.getColumns().add(nameColumn);
        studentTable.getColumns().add(majorColumn);
        studentTable.getColumns().add(emailColumn);
        studentTable.getColumns().add(phoneColumn);

        studentTable.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN
        );

        studentTable.setPlaceholder(
                new Label("No student data")
        );
    }

    private Label createStatusBar() {
        statusLabel.setMaxWidth(Double.MAX_VALUE);

        statusLabel.setPadding(
                new Insets(10, 15, 10, 15)
        );

        return statusLabel;
    }

    private void configureTableEvents() {
        studentTable
                .getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldStudent, newStudent) -> {
                            if (newStudent == null) {
                                statusLabel.setText(
                                        "No student selected"
                                );
                                return;
                            }

                            originalStudentId =
                                    newStudent.getStudentId();

                            studentIdField.setText(
                                    newStudent.getStudentId()
                            );

                            nameField.setText(
                                    newStudent.getName()
                            );

                            majorComboBox.setValue(
                                    newStudent.getMajor()
                            );

                            emailField.setText(
                                    newStudent.getEmail()
                            );

                            phoneField.setText(
                                    newStudent.getPhone()
                            );

                            statusLabel.setText(
                                    "Selected: "
                                            + newStudent.getName()
                                            + " - "
                                            + newStudent.getStudentId()
                            );
                        }
                );

        studentTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Student selected = studentTable
                        .getSelectionModel()
                        .getSelectedItem();

                if (selected != null) {
                    showAlert(
                            Alert.AlertType.INFORMATION,
                            "Student Detail",
                            "Student ID: "
                                    + selected.getStudentId()
                                    + "\nName: "
                                    + selected.getName()
                                    + "\nMajor: "
                                    + selected.getMajor()
                                    + "\nEmail: "
                                    + selected.getEmail()
                                    + "\nPhone: "
                                    + selected.getPhone()
                    );
                }
            }
        });
    }

    private void configurePhoneEvent() {
        phoneField.setOnKeyTyped(event -> {
            String character = event.getCharacter();

            if (!character.isEmpty()
                    && !Character.isDigit(
                            character.charAt(0)
                    )) {
                event.consume();
            }
        });
    }

    private void loadData() {
        try {
            studentTable.setItems(
                    FXCollections.observableArrayList(
                            studentDAO.getAll()
                    )
            );

            statusLabel.setText(
                    "Data loaded: "
                            + studentTable.getItems().size()
                            + " student(s)"
            );
        } catch (SQLException exception) {
            showDatabaseError(exception);
        }
    }

    private void addStudent() {
        Student student = getStudentFromForm();

        if (student == null) {
            return;
        }

        try {
            int affectedRows = studentDAO.insert(student);

            if (affectedRows > 0) {
                showAlert(
                        Alert.AlertType.INFORMATION,
                        "Success",
                        "Student data was added successfully."
                );

                loadData();
                clearForm();
            } else {
                showAlert(
                        Alert.AlertType.WARNING,
                        "Warning",
                        "No student data was added."
                );
            }
        } catch (SQLException exception) {
            if (exception.getErrorCode() == 1062) {
                showAlert(
                        Alert.AlertType.ERROR,
                        "Duplicate Student ID",
                        "The Student ID already exists."
                );
            } else {
                showDatabaseError(exception);
            }
        }
    }

    private void updateStudent() {
        if (originalStudentId == null) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "No Selection",
                    "Select a student from the table first."
            );

            return;
        }

        Student student = getStudentFromForm();

        if (student == null) {
            return;
        }

        try {
            int affectedRows = studentDAO.update(
                    student,
                    originalStudentId
            );

            if (affectedRows > 0) {
                showAlert(
                        Alert.AlertType.INFORMATION,
                        "Success",
                        "Student data was updated successfully."
                );

                loadData();
                clearForm();
            } else {
                showAlert(
                        Alert.AlertType.WARNING,
                        "Warning",
                        "No student data was updated."
                );
            }
        } catch (SQLException exception) {
            if (exception.getErrorCode() == 1062) {
                showAlert(
                        Alert.AlertType.ERROR,
                        "Duplicate Student ID",
                        "The new Student ID already exists."
                );
            } else {
                showDatabaseError(exception);
            }
        }
    }

    private Student getStudentFromForm() {
        String studentId =
                studentIdField.getText().trim();

        String name =
                nameField.getText().trim();

        String major =
                majorComboBox.getValue();

        String email =
                emailField.getText().trim();

        String phone =
                phoneField.getText().trim();

        if (studentId.isEmpty()
                || name.isEmpty()
                || major == null
                || email.isEmpty()
                || phone.isEmpty()) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Input",
                    "All fields must be completed."
            );

            return null;
        }

        if (!studentId.matches("\\d{5,10}")) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Student ID",
                    "Student ID must contain 5 to 10 digits."
            );

            return null;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        )) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Email",
                    "Enter a valid email address."
            );

            return null;
        }

        if (!phone.matches("\\d{8,15}")) {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Invalid Phone",
                    "Phone must contain 8 to 15 digits."
            );

            return null;
        }

        return new Student(
                studentId,
                name,
                major,
                email,
                phone
        );
    }

    private void clearForm() {
        studentIdField.clear();
        nameField.clear();
        majorComboBox.getSelectionModel().clearSelection();
        emailField.clear();
        phoneField.clear();

        studentTable
                .getSelectionModel()
                .clearSelection();

        originalStudentId = null;

        statusLabel.setText("Form cleared");
        studentIdField.requestFocus();
    }

    private void confirmExit() {
        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Are you sure you want to exit?",
                ButtonType.OK,
                ButtonType.CANCEL
        );

        confirmation.setTitle("Confirm Exit");
        confirmation.setHeaderText(null);

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    private void showDatabaseError(
            SQLException exception
    ) {
        showAlert(
                Alert.AlertType.ERROR,
                "Database Error",
                exception.getMessage()
        );
    }

    private void showAlert(
            Alert.AlertType type,
            String title,
            String message
    ) {
        Alert alert = new Alert(
                type,
                message,
                ButtonType.OK
        );

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}