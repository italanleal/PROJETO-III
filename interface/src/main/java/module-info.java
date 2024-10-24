module br.upe.UserInterface {
    requires javafx.controls;
    requires javafx.fxml;
    requires br.upe.controllers;
    requires persistencia;
    requires java.logging;

    exports br.upe.userinterface;
    opens br.upe.userinterface to javafx.fxml;
}
