package br.upe.userinterface;


import br.upe.util.persistencia.SystemException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubmissaoArtigoController {
    Logger logger = Logger.getLogger(SubmissaoArtigoController.class.getName());
    @FXML
    Label userEmail;
    @FXML
    private Button openFileButton;

    private FileChooser fileChooser;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());

        fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");

        // Optionally set an initial directory
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Optionally add file extensions
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Set up button action
        openFileButton.setOnAction(event -> openFile());
    }

    private void openFile() {
        // Open the FileChooser dialog
        Stage stage = (Stage) openFileButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile == null) {
            logger.log(Level.INFO, "No File Selected");
            return;
        }
        try {
            AppStateController.submissionController.submitFile(selectedFile);
        } catch (SystemException ignored) {
        }
        openFileButton.setDisable(true);
    }

    @FXML
    void goToHomeUser() throws IOException {
        App.setRoot("homeUser");
    }
    @FXML
    public void goToEventHome() throws IOException {
        App.setRoot("eventHome");
    }

    @FXML
    void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }
}
