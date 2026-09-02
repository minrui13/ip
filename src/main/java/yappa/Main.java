package yappa;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** Starts the JavaFX user interface for Yappa. */
public class Main extends Application {

    private final Yappa yappa = new Yappa();

    /**
     * Loads and displays the main application window.
     *
     * @param stage Primary JavaFX stage.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setYappa(yappa);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load MainWindow.fxml", e);
        }
    }

}
