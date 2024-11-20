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
        // Set the label's text to the value of the variable
        userEmail.setText(AppStateController.stateController.getCurrentUser().getEmail());
        Collection<Session> sessions = AppStateController.sessionController.getAllEventSessions(AppStateController.stateController.getCurrentEvent());

        VBox mainContainer = new VBox();

        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);


        sessions.forEach(session -> {
            VBox dataContainer = new VBox();
            Label descritor = new Label(session.getDescription());
            Label startDate = new Label((session.getStartDate() != null) ? DateFormat.getDateInstance().format(session.getStartDate()): "Não Informado");
            Label endDate = new Label((session.getEndDate() != null) ? DateFormat.getDateInstance().format(session.getEndDate()): "Não Informado");
            Label subscriptionsCount = new Label(String.valueOf(session.getSubscriptions().size()));
            dataContainer.getChildren().addAll(descritor, startDate, endDate, subscriptionsCount);

            VBox labelsContainer = new VBox();
            labelsContainer.getChildren().addAll(new Label("Nome da sessão"), new Label("Data de início"), new Label("Data de término"), new Label("Número de inscritos"));

            Button manageButton = new Button("manage");
            manageButton.setOnAction(a -> {
                try {
                    manageSession(session);
                } catch (IOException e){
                    logger.log(Level.SEVERE, "Error attaching session uuid to callback", e);
                }
            });

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

    @FXML private void manageSession(Session session) throws IOException {
        AppStateController.sessionController.changeCurrentSession(session);
        App.setRoot("sessionManager");
    }
}
