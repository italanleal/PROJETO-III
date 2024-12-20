package br.upe.userinterface;

import br.upe.entities.Event;
import br.upe.util.persistencia.SystemException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaDeEventosUSController {
    Logger logger = Logger.getLogger(ListaDeEventosUSController.class.getName());
    @FXML
    Label userEmail;

    @FXML
    ScrollPane scrollPane;

    @FXML
    private void initialize() {
        // Set the label's text to the current user's email
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());

        Collection<Event> events;

        events = AppStateController.eventController.getAllEvents();

        VBox mainContainer = new VBox();
        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setStyle("-fx-background-color: #ffffff;"); // Background for the main container

        events.forEach(event -> {
            VBox dataContainer = new VBox();
            dataContainer.setSpacing(5);

            Label descriptor = new Label(event.getDescription());
            Label director = new Label(event.getDirector());
            Label startDate = new Label(
                    (event.getStartDate() != null) ? event.getStartDate().toString() : "Não Informado"
            );
            Label endDate = new Label(
                    (event.getEndDate() != null) ? event.getEndDate().toString() : "Não Informado"
            );
            Label sessionCount = new Label(String.valueOf(event.getSessions().size()));

            // Apply styles to data labels
            descriptor.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            director.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            startDate.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            endDate.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            sessionCount.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");

            dataContainer.getChildren().addAll(descriptor, director, startDate, endDate, sessionCount);


            Button manageButton = new Button("saiba mais");
            VBox labelsContainer = new VBox();
            labelsContainer.getChildren().addAll(
                    new Label("Nome do Evento"),
                    new Label("Diretor do Evento"),
                    new Label("Data de início"),
                    new Label("Data de término"),
                    new Label("Número de Sessões")
            );
            labelsContainer.setSpacing(5);
            labelsContainer.setStyle("-fx-text-fill: #f2f2f2; -fx-font-size: 16;");


            manageButton.setStyle("-fx-background-color: #394159; -fx-text-fill: #f2f2f2; -fx-font-size: 14;");

            manageButton.setOnAction(a -> {
                try {
                    goToEventHome(event);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error attaching event UUID to callback", e);
                }
            });

            VBox buttonContainer = new VBox();
            buttonContainer.getChildren().addAll(manageButton);
            buttonContainer.setSpacing(15);

            HBox eventContainer = new HBox();
            eventContainer.setSpacing(25);
            eventContainer.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #ffffff; -fx-border-width: 1;");

            labelsContainer.setPrefWidth(100);
            dataContainer.setPrefWidth(100);
            buttonContainer.setPrefWidth(100);

            eventContainer.getChildren().addAll(labelsContainer, dataContainer, buttonContainer);
            mainContainer.getChildren().add(eventContainer);
        });

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    void goToHomeUser() throws IOException {
        App.setRoot("homeUser");
    }
    @FXML
    public void goToEventHome(Event event) throws IOException {
        AppStateController.eventController.changeCurrentEvent(event);
        App.setRoot("eventHome");
    }
    @FXML
    void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }

}

