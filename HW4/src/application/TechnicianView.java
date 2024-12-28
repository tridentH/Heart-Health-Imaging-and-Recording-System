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
import java.io.File;

public class TechnicianView {
	private Main app;
	private TextField patientIdField, totalScoreField, lmField, ladField, lcxField, rcaField, pdaField;

	public TechnicianView(Main app) {
		this.app = app;
	}

	public Scene getScene() {
		Label titleLabel = new Label("CT Scan Data Entry");

		//create fields and button
		patientIdField = new TextField();
		totalScoreField = new TextField();
		lmField = new TextField();
		ladField = new TextField();
		lcxField = new TextField();
		rcaField = new TextField();
		pdaField = new TextField();

		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: blue; -fx-text-fill: black;");

		saveButton.setOnAction(e -> saveCTScanData());

		//ui distances
		GridPane grid = new GridPane();
		grid.setVgap(10);
		grid.setHgap(10);
		grid.setPadding(new Insets(20));

		grid.add(titleLabel, 0, 0, 2, 1);

		grid.add(new Label("Patient ID:"), 0, 1);
		grid.add(patientIdField, 1, 1);

		grid.add(new Label("Total Agatston CAC Score:"), 0, 2);
		grid.add(totalScoreField, 1, 2);

		grid.add(new Label("Vessel Level Agatston CAC Score:"), 0, 3);

		grid.add(new Label("LM:"), 0, 4);
		grid.add(lmField, 1, 4);

		grid.add(new Label("LAD:"), 0, 5);
		grid.add(ladField, 1, 5);

		grid.add(new Label("LCX:"), 0, 6);
		grid.add(lcxField, 1, 6);

		grid.add(new Label("RCA:"), 0, 7);
		grid.add(rcaField, 1, 7);

		grid.add(new Label("PDA:"), 0, 8);
		grid.add(pdaField, 1, 8);

		grid.add(saveButton, 1, 9);
		GridPane.setHalignment(saveButton, HPos.RIGHT);

		return new Scene(grid, 500, 500);
	}

	private void saveCTScanData() {
		if (!validateFields()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Please fill all the fields.");
			return;
		}

		String patientId = patientIdField.getText();
		String patientInfoFilename = patientId + "_PatientInfo.txt";

		File patientInfoFile = new File(patientInfoFilename);
		if (!patientInfoFile.exists()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Patient ID does not exist.");
			return;
		}

		String ctResultsFilename = patientId + "CTResults.txt";

		//write to file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(ctResultsFilename))) {
			writer.write("PatientID:" + patientId);
			writer.newLine();
			writer.write("TotalAgatstonScore:" + totalScoreField.getText());
			writer.newLine();
			writer.write("LM:" + lmField.getText());
			writer.newLine();
			writer.write("LAD:" + ladField.getText());
			writer.newLine();
			writer.write("LCX:" + lcxField.getText());
			writer.newLine();
			writer.write("RCA:" + rcaField.getText());
			writer.newLine();
			writer.write("PDA:" + pdaField.getText());
			writer.newLine();
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "Error", "Could not save CT scan data.");
			return;
		}

		showAlert(Alert.AlertType.INFORMATION, "Success", "CT scan data saved.");
		app.showMainView();
	}

	//ensure fields filled out
	private boolean validateFields() {
		return !patientIdField.getText().isEmpty() && !totalScoreField.getText().isEmpty()
				&& !lmField.getText().isEmpty() && !ladField.getText().isEmpty() && !lcxField.getText().isEmpty()
				&& !rcaField.getText().isEmpty() && !pdaField.getText().isEmpty();
	}

	//show alert actions
	private void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
