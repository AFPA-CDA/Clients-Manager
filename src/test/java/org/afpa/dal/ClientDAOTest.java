package org.afpa.dal;

import org.afpa.dal.dao.ClientDAO;
import org.afpa.dal.models.Client;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class ClientDAOTest {
    public final ClientDAO clientDAO = new ClientDAO();

    @Test
    public void insert() throws SQLException {
        Client client = new Client();

        client.setAddress("65 rue imaginaire");
        client.setCity("Imaginaire");
        client.setFirstName("Beau");
        client.setLastName("Prénom");

        clientDAO.insert(client);

        ArrayList<Client> clients = clientDAO.list();

        Client foundClient = clientDAO.find(clients.get(clients.size() - 1).getId());

        Assert.assertEquals("65 rue imaginaire", foundClient.getAddress());
        Assert.assertEquals("Imaginaire", foundClient.getCity());
        Assert.assertEquals("Beau", foundClient.getFirstName());
        Assert.assertEquals("Prénom", foundClient.getLastName());
    }

    @Test
    public void delete() throws SQLException {
        ArrayList<Client> clients = clientDAO.list();

        Client foundClient = clientDAO.getLastClient();

        clientDAO.delete(foundClient.getId());

        Assert.assertNull(clientDAO.find(foundClient.getId()));
    }
}
