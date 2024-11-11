package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserSearchEventListViewLayoutController {

    @FXML
    private Label subscriptionIDLabel;

    @FXML
    private Label sessionIDLabel;

    @FXML
    private Label userIDLabel;

    @FXML
    private Label startDateLabel;

    @FXML
    private Label endDateLabel;

    void setAllLabels(int event){
        subscriptionIDLabel.setText(AppStateController.stateController.getCurrentSubscription().getUserUuid().toString());
        sessionIDLabel.setText(AppStateController.stateController.getCurrentSession().getEventUuid().toString());
        userIDLabel.setText(AppStateController.stateController.getCurrentUser().getUuid().toString());
        startDateLabel.setText(AppStateController.stateController.getCurrentEvent().getStartDate().toString());
        endDateLabel.setText(AppStateController.stateController.getCurrentEvent().getEndDate().toString());
    }
}
