package org.afpa.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.afpa.dal.dao.ClientDAO;
import org.afpa.dal.models.Client;
import org.afpa.dal.shared.ExceptionPrinter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public final class Index implements Initializable {
    private final ClientDAO clientDAO;
    private final ObservableList<Client> clientObservableList = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Client, String> firstName, lastName;

    @FXML
    private TableView<Client> clients;

    public Index() throws SQLException {
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

        this.firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        this.clients.setItems(clientObservableList);
    }
}
