module br.upe.UserInterface {
    requires javafx.controls;
    requires javafx.fxml;

    exports br.upe.UserInterface;
    opens br.upe.UserInterface to javafx.fxml;
}
