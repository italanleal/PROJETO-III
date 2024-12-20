package br.upe.userinterface;

import br.upe.entities.Session;
import br.upe.entities.Subscription;
import br.upe.entities.SystemUser;
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

public class ListaDeInscricoesController {

    Logger logger = Logger.getLogger(br.upe.userinterface.ListaDeSessoesController.class.getName());
    @FXML
    ScrollPane scrollPane;
    @FXML
    Label userEmail;

    @FXML
    private void initialize() {
        userEmail.setText(AppStateController.stateController.getCurrentUser().getName());
        Collection<Subscription> subscriptions = ((SystemUser) AppStateController.stateController.getCurrentUser()).getSubscriptions();

        VBox mainContainer = new VBox();
        mainContainer.getChildren().clear();
        mainContainer.setSpacing(10);
        mainContainer.setStyle("-fx-background-color: #ffffff;"); // Define o fundo do container principal


        subscriptions.forEach(subscription -> {
            Session session = subscription.getSession();
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

            Button cancelButton = new Button("Cancelar Inscrição");
            cancelButton.setStyle("-fx-background-color: #394159; -fx-text-fill: #f2f2f2; -fx-font-size: 14;");
            cancelButton.setOnAction(a -> {
                try {
                    unsubscripeToSession(subscription);
                } catch (IOException e) {
                    logger.log(Level.INFO, e.getMessage());
                }
            });

            VBox buttonContainer = new VBox();
            buttonContainer.setSpacing(15);
            buttonContainer.getChildren().add(cancelButton);

            HBox sessionContainer = new HBox();
            sessionContainer.setSpacing(25);
            sessionContainer.setStyle("-fx-background-color: #ffffff; -fx-padding: 10; -fx-border-color: #ffffff; -fx-border-width: 1;");

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

    private void unsubscripeToSession(Subscription subscription) throws IOException{
        AppStateController.userController.removeSubscriptionFromUser(subscription);
        App.setRoot("listaDeIncricoes");
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
}
