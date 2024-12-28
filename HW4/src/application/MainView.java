package application;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainView {
	private Main app;

	public MainView(Main app) {
		this.app = app;
	}

	public Scene getScene() {
		//title
		Label titleLabel = new Label("Welcome to Heart Health Imaging and Recording System");
		
		//button names
		Button patientIntakeButton = new Button("Patient Intake");
		Button ctScanTechButton = new Button("CT Scan Tech View");
		Button patientViewButton = new Button("Patient View");

		//blue buttons with black text
		patientIntakeButton.setStyle("-fx-background-color: blue; -fx-text-fill: black;");
		ctScanTechButton.setStyle("-fx-background-color: blue; -fx-text-fill: black;");
		patientViewButton.setStyle("-fx-background-color: blue; -fx-text-fill: black;");

		//button actions
		patientIntakeButton.setOnAction(e -> app.showReceptionistView());
		ctScanTechButton.setOnAction(e -> app.showTechnicianView());
		patientViewButton.setOnAction(e -> app.showPatientView());

		//vertical box to align title and buttons
		VBox vbox = new VBox(20, titleLabel, patientIntakeButton, ctScanTechButton, patientViewButton);
		vbox.setAlignment(Pos.CENTER);

		return new Scene(vbox, 400, 300);
	}
}
