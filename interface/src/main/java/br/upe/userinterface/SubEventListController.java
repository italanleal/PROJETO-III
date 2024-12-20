package br.upe.userinterface;

import br.upe.entities.SubEvent;
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

public class SubEventListController {
    Logger logger = Logger.getLogger(SubEventListController.class.getName());
    @FXML
    ScrollPane scrollPane;
    @FXML
    Label userEmail;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        userEmail.setText(AppStateController.stateController.getCurrentUser().getName());

        Collection<SubEvent> subEvents;
        subEvents = AppStateController.subEventController.getAllSubEvents();

        VBox mainContainer = new VBox();
        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setStyle("-fx-background-color: #f2f2f2;"); // Background color

        subEvents.forEach(subEvent -> {
            VBox dataContainer = new VBox();
            dataContainer.setSpacing(5);

            Label eventTitle = new Label(AppStateController.stateController.getCurrentEvent().getTitle());
            Label descritor = new Label(subEvent.getTitle());
            Label director = new Label(subEvent.getDirector());
            Label description = new Label(subEvent.getDescription());
            Label startDate = new Label((subEvent.getStartDate() != null) ? subEvent.getStartDate().toString() : "Não Informado");
            Label endDate = new Label((subEvent.getEndDate() != null) ? subEvent.getEndDate().toString() : "Não Informado");
            Label sessionCount = new Label(String.valueOf(subEvent.getSessions().size()));
            // Apply styles to labels
            String labelStyle = "-fx-text-fill: #394159; -fx-font-size: 16;";
            eventTitle.setStyle("-fx-text-fill: #394159; -fx-font-size: 18; -fx-font-family: 'System';");
            descritor.setStyle(labelStyle);
            director.setStyle(labelStyle);
            description.setStyle(labelStyle);
            startDate.setStyle(labelStyle);
            endDate.setStyle(labelStyle);
            sessionCount.setStyle(labelStyle);

            dataContainer.getChildren().addAll(eventTitle, descritor, director, startDate, endDate, description, sessionCount);

            VBox labelsContainer = new VBox();
            labelsContainer.getChildren().addAll(
                    new Label("Nome do Evento pai"),


                    new Label("Nome do SubEvento"),
                    new Label("Diretor do SubEvento"),
                    new Label("Data de início"),
                    new Label("Data de término"),
                    new Label("Descrição"),
                    new Label("Número de Sessões")
            );
            labelsContainer.setSpacing(5);
            labelsContainer.setStyle(labelStyle);


            Button manageButton = new Button("manage");
            manageButton.setStyle("-fx-background-color: #394159; -fx-text-fill: #f2f2f2; -fx-font-size: 14;");
            manageButton.setOnAction(a -> {
                try {
                    manageSubEvent(subEvent);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error attaching sub-event uuid to callback", e);
                }
            });

            Button deleteButton = new Button("delete");
            deleteButton.setStyle("-fx-background-color: #394159; -fx-text-fill: #f2f2f2; -fx-font-size: 14;");
            deleteButton.setOnAction(a -> {
                try {
                    deleteSubEvent(subEvent);
                } catch (IOException | SystemException e){
                    logger.log(Level.SEVERE, "Error deleting event", e);
                }
            });
            Button deleteButton = new Button("delete");
            deleteButton.setOnAction(a -> {
                try {
                    deleteSubEvent(subEvent);
                } catch (IOException | SystemException e){
                    logger.log(Level.SEVERE, "Error deleting event", e);
                }
            });

            VBox buttonContainer = new VBox();
            buttonContainer.getChildren().addAll(manageButton, deleteButton);
            buttonContainer.setSpacing(15);

            HBox eventContainer = new HBox();
            eventContainer.setSpacing(25);
            eventContainer.setStyle("-fx-background-color: #f2f2f2; -fx-padding: 10; -fx-border-color: #f2f2f2; -fx-border-width: 1;");
            labelsContainer.setPrefWidth(150);
            dataContainer.setPrefWidth(200);
            buttonContainer.setPrefWidth(150);


            eventContainer.getChildren().addAll(labelsContainer, dataContainer, buttonContainer);
            mainContainer.getChildren().add(eventContainer);
        });

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
    }

    private void deleteSubEvent(SubEvent subEvent) throws SystemException, IOException {
        AppStateController.subEventController.deleteSubEvent(subEvent);
        App.setRoot("subEventList");
    }

    @FXML
    private void manageSubEvent(SubEvent subEvent) throws IOException {
        AppStateController.subEventController.changeCurrentSubEvent(subEvent);
        App.setRoot("subEventManager");
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
    private void switchToSubEventRegister() throws IOException {
        App.setRoot("subEventRegister");
    }

    @FXML
    public void switchToEventManager() throws IOException {
        App.setRoot("eventManager");
    }
}