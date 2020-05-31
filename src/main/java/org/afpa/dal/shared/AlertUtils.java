package org.afpa.dal.shared;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * All the utils used for the alerts
 */
public final class AlertUtils {
    /**
     * Sends an alert to the user
     *
     * @param alertType   The type of the alert
     * @param contentText The content to display
     * @param title       The title of the alert
     */
    public static void alert(AlertType alertType, String contentText, String title) {
        Alert alert = new Alert(alertType);

        alert.setContentText(contentText);
        alert.setTitle(title);

        alert.showAndWait();
    }

    /**
     * Confirms an action through an alert
     *
     * @param buttons The buttons to use
     * @return The values from the confirmation
     */
    public static ButtonType confirm(ButtonType... buttons) {
        Alert alert = new Alert(AlertType.CONFIRMATION);

        alert.setContentText("Êtes vous sûr ?");
        alert.setTitle("Confirmation");

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(buttons);

        Optional<ButtonType> option = alert.showAndWait();

        return option.isEmpty() ? null : option.get();
    }
}
