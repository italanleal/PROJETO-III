package br.upe.userinterface;

import br.upe.entities.Submission;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Collection;

public class SubmissionListController {
    @FXML
    ScrollPane scrollPane;
    @FXML
    Label userEmail;

    @FXML
    private void initialize() {
        // Set the user email in the header
        userEmail.setText(AppStateController.stateController.getCurrentUser().getName());

        // Retrieve submissions
        Collection<Submission> submissions = AppStateController.stateController.getCurrentEvent().getSubmissions();
        VBox mainContainer = new VBox();
        mainContainer.setSpacing(15); // Add spacing between submission items
        mainContainer.setStyle("-fx-padding: 20; -fx-background-color: #f9f9f9;"); // Padding and background color

        submissions.forEach(submission -> {
            // Container for submission data
            VBox dataContainer = new VBox();
            dataContainer.setSpacing(5); // Space between data labels

            // Labels for submission details
            Label filenameLabel = new Label("Nome do arquivo: " + submission.getFilename());
            filenameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #394159;");

            Label userNameLabel = new Label("Usuário: " + (submission.getUser() != null ? submission.getUser().getName() : "N/A"));
            userNameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #394159;");

            Label emailLabel = new Label("Email: " + (submission.getUser() != null ? submission.getUser().getEmail() : "N/A"));
            emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #394159;");

            Label cpfLabel = new Label("CPF: " + (submission.getUser() != null ? submission.getUser().getCpf() : "N/A"));
            cpfLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #394159;");

            dataContainer.getChildren().addAll(filenameLabel, userNameLabel, emailLabel, cpfLabel);

            // Buttons for managing the file
            VBox buttonContainer = new VBox();
            buttonContainer.setSpacing(10); // Space between buttons

            Button manageButton = new Button("Ver Conteúdo");
            manageButton.setStyle("-fx-background-color: #394159; -fx-text-fill: white; -fx-font-size: 14px;");

            TextArea fileContentArea = new TextArea(); // Text area for file content
            fileContentArea.setEditable(false);
            fileContentArea.setVisible(false); // Initially hidden
            fileContentArea.setStyle("-fx-font-size: 14px; -fx-text-fill: #394159; -fx-border-color: #ccc;");

            manageButton.setOnAction(e -> {
                try {
                    String content = new String(submission.getContent());
                    fileContentArea.setText(content);
                    fileContentArea.setVisible(true); // Show the text area
                } catch (Exception ex) {
                    fileContentArea.setText("Erro ao carregar o conteúdo do arquivo.");
                    fileContentArea.setVisible(true);
                }
            });

            Button closeButton = new Button("Fechar");
            closeButton.setStyle("-fx-background-color: #ccc; -fx-text-fill: #394159; -fx-font-size: 14px;");
            closeButton.setOnAction(e -> {
                fileContentArea.setText(""); // Clear the content
                fileContentArea.setVisible(false); // Hide the text area
            });

            buttonContainer.getChildren().addAll(manageButton, closeButton);

            // Container for each submission
            HBox submissionContainer = new HBox(20, dataContainer, buttonContainer); // Space between data and buttons
            submissionContainer.setStyle("-fx-padding: 15; -fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-color: #ffffff;");

            // Add submission container and file content area
            mainContainer.getChildren().addAll(submissionContainer, fileContentArea);
        });

        // Add the main container to the scroll pane
        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
    }

    @FXML
    private void switchToSessionRegister() throws IOException {
        App.setRoot("sessionRegister");
    }

    @FXML
    private void switchToHomeAdmin() throws IOException {
        App.setRoot("homeAdmin");
    }
    @FXML
    private void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }
    @FXML
    private void switchToManageEvent() throws IOException {
        App.setRoot("eventManager");
    }
}
