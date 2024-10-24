package br.upe.userinterface;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class EventRegisterController extends HomeAdminController {
    @FXML
    Label warningLabel;
    @FXML
    TextField descritorField;
    @FXML
    TextField directorField;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;
    @FXML
    private void registerEvent() throws IOException {
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = (startDatePicker.getValue() != null) ? DateFormat.getDateInstance().parse(startDatePicker.getValue().toString()): null;
            endDate = (endDatePicker.getValue() != null) ? DateFormat.getDateInstance().parse(endDatePicker.getValue().toString()): null;

        } catch (Exception e) {
            //implementar logger
        }

        if(!descritorField.getText().isEmpty() && !directorField.getText().isEmpty()){
            boolean isCreated = AppStateController.eventController.createNewEvent(descritorField.getText(), directorField.getText());
            if(isCreated){
               if(startDate != null) AppStateController.eventController.updateEventStartDate(startDate);
               if(endDate != null) AppStateController.eventController.updateEventEndDate(endDate);

               App.setRoot("eventManager");
            } else {
                warningLabel.setText("Couldn't create new event");
            }
        } else {
            warningLabel.setText("Couldn't create new event");
        }
    }
}
