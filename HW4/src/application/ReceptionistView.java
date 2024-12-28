package application;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ReceptionistView {
	private Main app;
	private TextField firstNameField, lastNameField, emailField, phoneField, healthHistoryField, insuranceIdField;

	public ReceptionistView(Main app) {
		this.app = app;
	}

	public Scene getScene() {
		
		Label titleLabel = new Label("Patient Intake Form");

		//create text fields and save button
		firstNameField = new TextField();
		lastNameField = new TextField();
		emailField = new TextField();
		phoneField = new TextField();
		healthHistoryField = new TextField();
		insuranceIdField = new TextField();

		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: blue; -fx-text-fill: black;");

		saveButton.setOnAction(e -> savePatientInfo());
		
		//set ui distances
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(20));

		grid.add(titleLabel, 0, 0, 2, 1);

		grid.add(new Label("First Name:"), 0, 1);
		grid.add(firstNameField, 1, 1);

		grid.add(new Label("Last Name:"), 0, 2);
		grid.add(lastNameField, 1, 2);

		grid.add(new Label("Email:"), 0, 3);
		grid.add(emailField, 1, 3);

		grid.add(new Label("Phone Number:"), 0, 4);
		grid.add(phoneField, 1, 4);

		grid.add(new Label("Health History:"), 0, 5);
		grid.add(healthHistoryField, 1, 5);

		grid.add(new Label("Insurance ID:"), 0, 6);
		grid.add(insuranceIdField, 1, 6);

		grid.add(saveButton, 1, 7);
		GridPane.setHalignment(saveButton, HPos.RIGHT);

		return new Scene(grid, 500, 400);
	}

	private void savePatientInfo() {
		if (!validateFields()) {
			showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all the fields.");
			return;
		}

		String patientId = generatePatientID();
		String filename = patientId + "_PatientInfo.txt";

		//write to file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			writer.write("PatientID:" + patientId);
			writer.newLine();
			writer.write("FirstName:" + firstNameField.getText());
			writer.newLine();
			writer.write("LastName:" + lastNameField.getText());
			writer.newLine();
			writer.write("Email:" + emailField.getText());
			writer.newLine();
			writer.write("PhoneNumber:" + phoneField.getText());
			writer.newLine();
			writer.write("HealthHistory:" + healthHistoryField.getText());
			writer.newLine();
			writer.write("InsuranceID:" + insuranceIdField.getText());
			writer.newLine();
			writer.write("ExamDate:" + java.time.LocalDate.now());
			writer.newLine();
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "Error", "Could not save patient info.");
			return;
		}

		showAlert(Alert.AlertType.INFORMATION, "Success", "Patient ID: " + patientId + " saved.");
		app.showMainView();
	}

	//ensure fields are true
	private boolean validateFields() {
		return !firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty()
				&& !emailField.getText().isEmpty() && !phoneField.getText().isEmpty()
				&& !healthHistoryField.getText().isEmpty() && !insuranceIdField.getText().isEmpty();
	}

	private String generatePatientID() {
		Random rand = new Random();
		int patientId = rand.nextInt(90000) + 10000; //generates a random 5-digit number
		return String.valueOf(patientId);
	}

	//alerts for actions
	private void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
