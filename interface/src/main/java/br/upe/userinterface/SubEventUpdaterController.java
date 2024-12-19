package br.upe.userinterface;

import br.upe.util.persistencia.SystemException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubEventUpdaterController {
    Logger logger = Logger.getLogger(SubEventUpdaterController.class.getName());
    @FXML
    Label eventDescritor;
    @FXML
    Label titleField;

    @FXML
    private void initialize() {
        // Set the label's text to the value of the variable
        eventDescritor.setText(AppStateController.stateController.getCurrentSubEvent().getTitle());
    }
    @FXML
    TextField directorField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    TextField descritorField;

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
    private void switchToEventUpdater() throws IOException {
        App.setRoot("eventUpdater");
    }
    @FXML
    private void updateSubEvent() throws IOException, SystemException {
        LocalDate startDate = null;
        LocalDate endDate = null;
        try {
            startDate = (startDatePicker.getValue() != null) ? startDatePicker.getValue(): null;
            endDate = (endDatePicker.getValue() != null) ? endDatePicker.getValue(): null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error parsing Date objects", e);
        }
        if (startDate != null) AppStateController.subEventController.updateSubEventStartDate(startDate);
        if (endDate != null) AppStateController.subEventController.updateSubEventEndDate(endDate);
        if (!directorField.getText().isEmpty())
            AppStateController.subEventController.updateSubEventDirector(directorField.getText());
        if (!titleField.getText().isEmpty())
            AppStateController.subEventController.updateSubEventDescription(titleField.getText());
        if (!descritorField.getText().isEmpty())
            AppStateController.subEventController.updateSubEventDescription(descritorField.getText());

        App.setRoot("subEventManager");
    }
    @FXML
    private void switchToManageSubEvent() throws IOException{
        App.setRoot("manageSubEvent");
    }
    @FXML
    private void switchToManageEvent() throws IOException{
        App.setRoot("manageEvent");
    }
}
