module br.upe.UserInterface {
    requires javafx.controls;
    requires javafx.fxml;
    requires br.upe.controllers;

    exports br.upe.UserInterface;
    opens br.upe.UserInterface to javafx.fxml;
}
