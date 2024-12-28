package application;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PatientView {
	private Main app;
	private TextField patientIdField;
	private Label resultLabel;

	public PatientView(Main app) {
		this.app = app;
	}

	public Scene getScene() {
		Label titleLabel = new Label("Enter Patient ID to View Results");
		patientIdField = new TextField();
		Button viewButton = new Button("View");
		resultLabel = new Label();

		viewButton.setOnAction(e -> showPatientData());

		VBox vbox = new VBox(10, titleLabel, patientIdField, viewButton, resultLabel);
		vbox.setPadding(new Insets(20));
		vbox.setAlignment(Pos.CENTER);

		return new Scene(vbox, 500, 400);
	}

	private void showPatientData() {
		String patientId = patientIdField.getText();
		if (patientId.isEmpty()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Please enter a Patient ID.");
			return;
		}

		String patientInfoFilename = patientId + "_PatientInfo.txt";
		String ctResultsFilename = patientId + "CTResults.txt";

		File patientInfoFile = new File(patientInfoFilename);
		if (!patientInfoFile.exists()) {
			showAlert(Alert.AlertType.ERROR, "Error", "Wrong Patient ID.");
			return;
		}

		File ctResultsFile = new File(ctResultsFilename);
		if (!ctResultsFile.exists()) {
			showAlert(Alert.AlertType.INFORMATION, "Information", "No data is available yet.");
			return;
		}

		//use a map to link patient info
		Map<String, String> patientInfo = readDataFromFile(patientInfoFilename);
		Map<String, String> ctResults = readDataFromFile(ctResultsFilename);

		String patientName = patientInfo.get("FirstName") + " " + patientInfo.get("LastName");

		StringBuilder sb = new StringBuilder();
		sb.append("Hello ").append(patientName).append("\n\n");
		sb.append("The total Agatston score: ").append(ctResults.get("TotalAgatstonScore")).append("\n");
		sb.append("LM: ").append(ctResults.get("LM")).append("\n");
		sb.append("LAD: ").append(ctResults.get("LAD")).append("\n");
		sb.append("LCX: ").append(ctResults.get("LCX")).append("\n");
		sb.append("RCA: ").append(ctResults.get("RCA")).append("\n");
		sb.append("PDA: ").append(ctResults.get("PDA")).append("\n");

		resultLabel.setText(sb.toString());
	}

	//read data from file
	private Map<String, String> readDataFromFile(String filename) {
		Map<String, String> data = new HashMap<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(":", 2);
				if (parts.length == 2) {
					data.put(parts[0], parts[1]);
				}
			}
		} catch (IOException e) {
			showAlert(Alert.AlertType.ERROR, "Error", "Could not read data from file.");
		}
		return data;
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
