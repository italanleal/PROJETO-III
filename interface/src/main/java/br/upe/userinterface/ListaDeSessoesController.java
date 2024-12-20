package br.upe.userinterface;

import br.upe.entities.Event;
import br.upe.entities.Session;
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
public class ListaDeSessoesController {

    Logger logger = Logger.getLogger(br.upe.userinterface.ListaDeSessoesController.class.getName());
    @FXML
    ScrollPane scrollPane;
    @FXML
    Label userEmail;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        userEmail.setText(AppStateController.stateController.getCurrentUser().getName());
        Collection<Session> sessions = AppStateController.sessionController.getAllEventSessions();

        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);

        sessions.forEach(session -> {
            VBox dataContainer = new VBox();
            Label title = new Label(session.getTitle());
            Label guest = new Label(session.getGuest());
            Label local = new Label(session.getLocal());

            Label startDate = new Label((session.getStartDate() != null) ? session.getStartDate().toString(): "Não Informado");
            Label endDate = new Label((session.getEndDate() != null) ? session.getEndDate().toString(): "Não Informado");
            Label subscriptionsCount = new Label(String.valueOf(session.getSubscriptions().size()));
            Label descritor = new Label(session.getDescription());
            dataContainer.getChildren().addAll(title, guest, local, startDate, endDate, subscriptionsCount, descritor);

            VBox labelsContainer = new VBox();
            labelsContainer.getChildren().addAll(new Label("Nome da sessão"),
                    new Label("Nome da convidado"),
                    new Label("Local"),
                    new Label("Data de início"),
                    new Label("Data de término"),
                    new Label("Número de inscritos"),
                    new Label("Descrição"));


            Button manageButton = new Button("Inscrever-se");
            manageButton.setOnAction(a -> {
                try {
                    subscripeToSession(session);
                } catch (IOException e) {
                    logger.log(Level.INFO, e.getMessage());
                }
            });

            if(AppStateController.sessionController.userIsSubscribed(session)) {
                manageButton.setDisable(true);
            }

            VBox buttonContainer = new VBox();
            buttonContainer.getChildren().add(manageButton);

            HBox sessionContainer = new HBox();
            sessionContainer.setSpacing(25);
            labelsContainer.setPrefWidth(100);
            dataContainer.setPrefWidth(100);
            buttonContainer.setPrefWidth(100);

            sessionContainer.getChildren().addAll(labelsContainer, dataContainer, buttonContainer);
            mainContainer.getChildren().add(sessionContainer);

        });

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

    private void subscripeToSession(Session session) throws IOException{
        AppStateController.sessionController.changeCurrentSession(session);
        AppStateController.sessionController.addSubscriptionToSession();
        App.setRoot("listaDeSessoes");
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
