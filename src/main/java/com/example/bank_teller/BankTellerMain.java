package com.example.bank_teller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 The main class which extends the javafx.application.Application class. It is essentially
 the main entry point of this application.
 @author Maryam, Nabihah
 */

public class BankTellerMain extends Application {
    /**
     * This method overrides the start method and launches stage(window) containing all objects
     * of the JavaFX application.
     * @param stage, stage where application will be set.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("bankTeller-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Bank Teller");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method launches the application
     * @param args, command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}