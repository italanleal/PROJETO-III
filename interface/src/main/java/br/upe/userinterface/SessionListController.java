package br.upe.userinterface;

import br.upe.entities.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SessionListController {
    Logger logger = Logger.getLogger(SessionListController.class.getName());
    @FXML
    ScrollPane scrollPane;
    @FXML
    Label userEmail;

    @FXML
    private void initialize() {
        // Set the label's text to the current user's name
        userEmail.setText(AppStateController.stateController.getCurrentUser().getName());

        Collection<Session> sessions = AppStateController.sessionController.getAllEventSessions();

        VBox mainContainer = new VBox();
        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setStyle("-fx-background-color: #ffffff;"); // Background for the main container

        sessions.forEach(session -> {
            VBox dataContainer = new VBox();
            dataContainer.setSpacing(5);

            Label title = new Label(session.getTitle());
            Label guest = new Label(session.getGuest());
            Label local = new Label(session.getLocal());
            Label startDate = new Label((session.getStartDate() != null) ? session.getStartDate().toString() : "Não Informado");
            Label endDate = new Label((session.getEndDate() != null) ? session.getEndDate().toString() : "Não Informado");
            Label subscriptionsCount = new Label(String.valueOf(session.getSubscriptions().size()));
            Label descritor = new Label(session.getDescription());

            // Apply styles to data labels
            title.setStyle("-fx-text-fill: #394159; -fx-font-size: 18; -fx-font-family: 'System Bold';");
            guest.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            local.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            startDate.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            endDate.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            subscriptionsCount.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            descritor.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");

            dataContainer.getChildren().addAll(title, guest, local, startDate, endDate, subscriptionsCount, descritor);

            VBox labelsContainer = new VBox();
            labelsContainer.getChildren().addAll(
                    new Label("Nome da sessão"),
                    new Label("Nome do convidado"),
                    new Label("Local"),
                    new Label("Data de início"),
                    new Label("Data de término"),
                    new Label("Número de inscritos"),
                    new Label("Descrição")
            );
            labelsContainer.setSpacing(5);
            labelsContainer.setStyle("-fx-text-fill: #f2f2f2; -fx-font-size: 16;");

            Button manageButton = new Button("Manage");
            manageButton.setStyle("-fx-background-color: #394159; -fx-text-fill: #f2f2f2; -fx-font-size: 14;");
            manageButton.setOnAction(a -> {
                try {
                    manageSession(session);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error attaching session uuid to callback", e);
                }
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #394159; -fx-text-fill: #f2f2f2; -fx-font-size: 14;");
            deleteButton.setOnAction(a -> {
                try {
                    deleteSession(session);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error deleting session", e);
                }
            });

            VBox buttonContainer = new VBox();
            buttonContainer.getChildren().addAll(manageButton, deleteButton);
            buttonContainer.setSpacing(15);

            HBox sessionContainer = new HBox();
            sessionContainer.setSpacing(25);
            sessionContainer.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #ffffff; -fx-border-width: 1;");

            labelsContainer.setPrefWidth(100);
            dataContainer.setPrefWidth(100);
            buttonContainer.setPrefWidth(100);

            sessionContainer.getChildren().addAll(labelsContainer, dataContainer, buttonContainer);
            mainContainer.getChildren().add(sessionContainer);
        });

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
    }


    private void deleteSession(Session session) throws IOException {
        AppStateController.sessionController.deleteSession(session);
        App.setRoot("sessionList");
    }

    @FXML
    private void switchToSessionRegister() throws IOException {
        App.setRoot("sessionRegister");
    }
    @FXML
    private void switchToManageEvent() throws IOException {
        App.setRoot("eventManager");
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
    private void manageSession(Session session) throws IOException {
        AppStateController.sessionController.changeCurrentSession(session);
        App.setRoot("sessionManager");
    }
}
