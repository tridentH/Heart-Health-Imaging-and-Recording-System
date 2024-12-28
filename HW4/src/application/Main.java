package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        MainView mainView = new MainView(this);
        primaryStage.setTitle("Heart Health Imaging and Recording System");
        primaryStage.setScene(mainView.getScene());
        primaryStage.show();
    }
    
    public void showReceptionistView() {
        ReceptionistView receptionistView = new ReceptionistView(this);
        primaryStage.setScene(receptionistView.getScene());
    }

    public void showTechnicianView() {
        TechnicianView technicianView = new TechnicianView(this);
        primaryStage.setScene(technicianView.getScene());
    }

    public void showPatientView() {
        PatientView patientView = new PatientView(this);
        primaryStage.setScene(patientView.getScene());
    }

    public void showMainView() {
        MainView mainView = new MainView(this);
        primaryStage.setScene(mainView.getScene());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
