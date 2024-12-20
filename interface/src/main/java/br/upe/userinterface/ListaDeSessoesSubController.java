package br.upe.userinterface;

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

public class ListaDeSessoesSubController {

    Logger logger = Logger.getLogger(br.upe.userinterface.ListaDeSessoesSubController.class.getName());
    @FXML
    ScrollPane scrollPane;
    @FXML
    Label userEmail;

    @FXML
    private void initialize() {
        // Define o texto do rótulo com o nome do usuário atual
        userEmail.setText(AppStateController.stateController.getCurrentUser().getName());
        Collection<Session> sessions = AppStateController.subEventController.getSubEventSessions();

        VBox mainContainer = new VBox();
        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setStyle("-fx-background-color: #ffffff;"); // Fundo branco para o container principal

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

            // Aplicação de estilos nos rótulos de dados
            title.setStyle("-fx-text-fill: #394159; -fx-font-size: 18; -fx-font-family: 'System Bold';");
            guest.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            local.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            startDate.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            endDate.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            subscriptionsCount.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            descritor.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");

            dataContainer.getChildren().addAll(title, guest, local, startDate, endDate, subscriptionsCount, descritor);

            VBox labelsContainer = new VBox();
            labelsContainer.setSpacing(5);
            labelsContainer.setStyle("-fx-text-fill: #394159; -fx-font-size: 16;");
            labelsContainer.getChildren().addAll(
                    new Label("Nome da Sessão"),
                    new Label("Convidado"),
                    new Label("Local"),
                    new Label("Data de Início"),
                    new Label("Data de Término"),
                    new Label("Número de Inscritos"),
                    new Label("Descrição")
            );

            Button manageButton = new Button("Inscrever-se");
            manageButton.setStyle("-fx-background-color: #394159; -fx-text-fill: #f2f2f2; -fx-font-size: 14;");
            manageButton.setOnAction(a -> {
                try {
                    subscripeToSession(session);
                } catch (IOException e) {
                    logger.log(Level.INFO, e.getMessage());
                }
            });

            if (AppStateController.sessionController.userIsSubscribed(session)) {
                manageButton.setDisable(true);
            }

            VBox buttonContainer = new VBox();
            buttonContainer.setSpacing(15);
            buttonContainer.getChildren().add(manageButton);

            HBox sessionContainer = new HBox();
            sessionContainer.setSpacing(25);
            sessionContainer.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #f2f2f2; -fx-border-width: 1;");

            labelsContainer.setPrefWidth(100);
            dataContainer.setPrefWidth(200);
            buttonContainer.setPrefWidth(100);

            sessionContainer.getChildren().addAll(labelsContainer, dataContainer, buttonContainer);
            mainContainer.getChildren().add(sessionContainer);
        });

        scrollPane.setContent(mainContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
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
    void logout() throws IOException {
        AppStateController.authController.logout();
        App.setRoot("login");
    }

    public void goToSubEventHome() throws IOException {
        App.setRoot("subEventHome");
    }
}
