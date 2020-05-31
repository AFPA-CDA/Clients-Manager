package org.afpa.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.afpa.dal.dao.ClientDAO;
import org.afpa.dal.models.Client;
import org.afpa.dal.shared.AlertUtils;
import org.afpa.dal.shared.ExceptionPrinter;
import org.afpa.dal.shared.Validator;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public final class Index implements Initializable {
    private final ClientDAO clientDAO;
    private final ObservableList<Client> clientObservableList = FXCollections.observableArrayList();

    @FXML
    private TextField addressText, cityText, firstNameText, lastNameText;

    @FXML
    private TableColumn<Client, String> firstName, lastName;

    @FXML
    private TableView<Client> clients;

    public Index() {
        this.clientDAO = new ClientDAO();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initializes the ArrayList
        ArrayList<Client> databaseClients = null;

        try {
            // Fills an ArrayList with all the clients on the database
            databaseClients = clientDAO.list();
        } catch (SQLException e) {
            // Pretty prints the exception
            new ExceptionPrinter<>(e).print();
        }

        if (databaseClients != null) {
            // Adds all the clients from the database to the client's observable list
            this.clientObservableList.addAll(databaseClients);
        }

        // Defines the value the TableColumns will have to observe for changes
        this.firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // Set the items for the clients TableView
        this.clients.setItems(clientObservableList);

        // Fills the form with the information of the selected client on the TableView
        clients.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Client>) c -> {
            addressText.setText(c.getList().get(0).getAddress());
            cityText.setText(c.getList().get(0).getCity());
            firstNameText.setText(c.getList().get(0).getFirstName());
            lastNameText.setText(c.getList().get(0).getLastName());
        });
    }

    /**
     * Adds a client from the database and TableView on add button action
     */
    @FXML
    private void add() {
        Client client = new Client();

        client.setAddress(addressText.getText());
        client.setCity(cityText.getText());
        client.setFirstName(firstNameText.getText());
        client.setLastName(lastNameText.getText());

        // Validates the client
        boolean isValid = Validator.validateClient(client);

        try {
            if (isValid) {
                // Inserts the new client into the database
                clientDAO.insert(client);

                // Adds the client to the TableView
                clientObservableList.add(client);
            } else {
                AlertUtils.alert(Alert.AlertType.ERROR, "Certains de vos champs contiennent des erreurs.", "Erreur - Ajout");
            }
        } catch (SQLException e) {
            // Pretty prints the exception
            new ExceptionPrinter<>(e).print();
        }
    }

    /**
     * Deletes an client from the database and TableView on delete button action
     */
    @FXML
    private void delete() {
        // If the user has select a client
        if (!clients.getSelectionModel().getSelectedItems().isEmpty()) {
            try {
                // Creates the buttons type for the confirmation
                ButtonType cancel = new ButtonType("Annuler");
                ButtonType confirm = new ButtonType("Confirmer");

                // Sends the confirmation to the user
                ButtonType isConfirmed = AlertUtils.confirm(cancel, confirm);

                // If the user confirmed
                if (isConfirmed.equals(confirm)) {
                    // Deletes the client from the database
                    clientDAO.delete(clients.getSelectionModel().getSelectedItem().getId());

                    // Removes the client from the TableView
                    clientObservableList.remove(clients.getSelectionModel().getSelectedIndex());

                    // Sends an information alert to the user
                    AlertUtils.alert(Alert.AlertType.INFORMATION, "Le client à été supprimé", "Suppression - Client");
                } else {
                    // Sends an information alert to the user
                    AlertUtils.alert(Alert.AlertType.INFORMATION, "La suppression à été annulée.", "Annulation - Suppresion");
                }
            } catch (SQLException e) {
                // Pretty prints the exception
                new ExceptionPrinter<>(e).print();
            }
        } else {
            // Otherwise sends an Error alert to the user
            AlertUtils.alert(Alert.AlertType.ERROR, "Vous devez choisir un client à supprimer.", "Erreur - Selection");
        }
    }

    /**
     * Updates a client from the database on update button action
     */
    @FXML
    private void modify() {
        // If the user has select a client
        if (!clients.getSelectionModel().getSelectedItems().isEmpty()) {
            try {
                // Retreives the client with the given id
                Client updatedClient = clientDAO.find(clients.getSelectionModel().getSelectedItem().getId());

                if (updatedClient != null) {
                    updatedClient.setAddress(addressText.getText());
                    updatedClient.setCity(cityText.getText());
                    updatedClient.setFirstName(firstNameText.getText());
                    updatedClient.setLastName(lastNameText.getText());

                    // Validates the client
                    boolean isValid = Validator.validateClient(updatedClient);

                    if (isValid) {
                        // Updates the client from the database
                        clientDAO.update(updatedClient);

                        // Workaround for refreshing TableView
                        clientObservableList.remove(clients.getSelectionModel().getSelectedIndex());
                        clientObservableList.add(updatedClient);

                        // Sends an information alert to the user
                        AlertUtils.alert(Alert.AlertType.INFORMATION, "Le client à été mis à jour", "Mis à Jour - Client");
                    } else {
                        AlertUtils.alert(Alert.AlertType.ERROR, "Certains de vos champs contiennent des erreurs.", "Erreur - Ajout");
                    }
                }
            } catch (SQLException e) {
                // Pretty prints the exception
                new ExceptionPrinter<>(e).print();
            }
        } else {
            // Otherwise sends an Error alert to the user
            AlertUtils.alert(Alert.AlertType.ERROR, "Vous devez choisir un client à mettre à jour.", "Erreur - Selection");
        }
    }
}
