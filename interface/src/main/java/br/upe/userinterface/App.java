package br.upe.userinterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

//.
/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;


    private static final Logger logger = Logger.getLogger(App.class.getName());

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("AcademicEvents");

        InputStream faviconStream = App.class.getResourceAsStream("/favicon_academic_events.png");
        if (faviconStream == null) {
            logger.log(Level.SEVERE, "Favicon resource could not be found!");
        } else {
            Image icon = new Image(faviconStream);
            stage.getIcons().add(icon);
        }

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}