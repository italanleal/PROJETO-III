package br.upe.userinterface;

import br.upe.entities.Event;
import br.upe.util.persistencia.SystemException;
import javafx.event.ActionEvent;
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

public class ListaDeEventosUSController {
    Logger logger = Logger.getLogger(ListaDeEventosUSController.class.getName());
    @FXML
    Label userEmail;

    @FXML
    ScrollPane scrollPane;

    @FXML
    private void initialize() throws SystemException {
        // Set the label's text to the value of the variable
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());
        Collection<Event> events = AppStateController.eventController.getAllEvents();

        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);


        events.forEach(event -> {
            VBox dataContainer = new VBox();
            Label descritor = new Label(event.getDescription());
            Label director = new Label(event.getDirector());
            Label startDate = new Label((event.getStartDate() != null) ? event.getStartDate().toString(): "Não Informado");
            Label endDate = new Label((event.getEndDate() != null) ? event.getEndDate().toString(): "Não Informado");
            Label sessionCount = new Label(String.valueOf(event.getSessions().size()));
            dataContainer.getChildren().addAll(descritor, director, startDate, endDate, sessionCount);

            VBox labelsContainer = new VBox();
            labelsContainer.getChildren().addAll(new Label("Nome do Evento"), new Label("Diretor do Evento"), new Label("Data de início"), new Label("Data de término"), new Label("Número de Sessões"));

            Button manageButton = new Button("manage");
            manageButton.setOnAction(a -> {
                try {
                    goToEventHome(event);
                } catch (IOException e){
                    logger.log(Level.SEVERE, "Error attaching event uuid to callback", e);
                }
            });

            VBox buttonContainer = new VBox();
            buttonContainer.getChildren().add(manageButton);

            HBox eventContainer = new HBox();
            eventContainer.setSpacing(25);
            labelsContainer.setPrefWidth(100);
            dataContainer.setPrefWidth(100);
            buttonContainer.setPrefWidth(100);

            eventContainer.getChildren().addAll(labelsContainer, dataContainer, buttonContainer);
            mainContainer.getChildren().add(eventContainer);

        });

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }
    @FXML
    void goToHomeUser() throws IOException {
        App.setRoot("homeUser");
    }
    @FXML
    void goToEventHome(Event event) throws IOException {
        AppStateController.eventController.changeCurrentEvent(event);
        App.setRoot("eventHome");
    }
    @FXML
    void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }

    public void goToListaDeEventos() throws IOException {

    }
}
