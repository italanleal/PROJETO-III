package br.upe.UserInterface;

import br.upe.pojos.GreatEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventListController {
    Logger logger = Logger.getLogger(EventRegisterController.class.getName());
    @FXML
    ScrollPane scrollPane;
    @FXML
    Label userEmail;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());
        Collection<GreatEvent> events = AppStateController.eventController.getAllEventsByUser();

        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);


        events.forEach(event -> {
            VBox dataContainer = new VBox();
            Label descritor = new Label(event.getDescritor());
            Label director = new Label(event.getDirector());
            Label startDate = new Label((event.getStartDate() != null) ? DateFormat.getDateInstance().format(event.getStartDate()): "Não Informado");
            Label endDate = new Label((event.getEndDate() != null) ? DateFormat.getDateInstance().format(event.getEndDate()): "Não Informado");
            Label sessionCount = new Label(String.valueOf(event.getSessions().size()));
            dataContainer.getChildren().addAll(descritor, director, startDate, endDate, sessionCount);

            VBox labelsContainer = new VBox();
            labelsContainer.getChildren().addAll(new Label("Nome do Evento"), new Label("Diretor do Evento"), new Label("Data de início"), new Label("Data de término"), new Label("Número de Sessões"));

            Button manageButton = new Button("manage");
            manageButton.setOnAction(a -> {
                try {
                    manageEvent(event.getUuid());
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
    private void switchToEventRegister() throws IOException {
        App.setRoot("eventRegister");
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

    @FXML private void manageEvent(UUID eventUuid) throws IOException {
        AppStateController.eventController.changeCurrentEvent(eventUuid);
        App.setRoot("eventManager");
    }
}
