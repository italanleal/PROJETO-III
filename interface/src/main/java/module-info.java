module br.upe.UserInterface {
    requires javafx.controls;
    requires javafx.fxml;
    requires br.upe.controllers;
    requires persistencia;

    exports br.upe.UserInterface;
    opens br.upe.UserInterface to javafx.fxml;
}
