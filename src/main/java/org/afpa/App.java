package org.afpa;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.afpa.dal.shared.AlertUtils;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("index"));

        stage.setOnCloseRequest(event -> {
            ButtonType cancel = new ButtonType("Annuler");
            ButtonType confirm = new ButtonType("Confirmer");

            ButtonType isConfirmed = AlertUtils.confirm(cancel, confirm);

            if (isConfirmed.equals(confirm)) {
                AlertUtils.alert(Alert.AlertType.INFORMATION, "Aurevoir !", "Aurevoir");
            } else {
                AlertUtils.alert(Alert.AlertType.INFORMATION, "Bon retour parmi nous !", "Bienvenue");
            }
        });

        stage.setResizable(false);
        stage.setTitle("Clients - DAO");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads an FXML file
     *
     * @param fxml The fxml file to load
     * @return The loaded file
     * @throws IOException If the function can't read the file
     */
    private static Parent loadFXML(String fxml) throws IOException {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml")).load();
    }
}