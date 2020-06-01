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

        Client foundClient = clientDAO.getLastClient();

        Assert.assertEquals("65 rue imaginaire", foundClient.getAddress());
        Assert.assertEquals("Imaginaire", foundClient.getCity());
        Assert.assertEquals("Beau", foundClient.getFirstName());
        Assert.assertEquals("Prénom", foundClient.getLastName());
    }

    @Test
    public void update() throws SQLException {
        Client updatedClient = clientDAO.getLastClient();

        updatedClient.setAddress("La rue de l'update");
        updatedClient.setCity("Updatia");
        updatedClient.setFirstName("Jean");
        updatedClient.setLastName("Update");

        clientDAO.update(updatedClient);

        Client newClient = clientDAO.getLastClient();

        Assert.assertEquals("La rue de l'update", newClient.getAddress());
        Assert.assertEquals("Updatia", newClient.getCity());
        Assert.assertEquals("Jean", newClient.getFirstName());
        Assert.assertEquals("Update", newClient.getLastName());
    }

    @Test
    public void find() throws SQLException {
        Client client = clientDAO.find(clientDAO.getLastClient().getId());

        Assert.assertEquals("La rue de l'update", client.getAddress());
        Assert.assertEquals("Updatia", client.getCity());
        Assert.assertEquals("Jean", client.getFirstName());
        Assert.assertEquals("Update", client.getLastName());
    }

    @Test
    public void list() throws SQLException {
        ArrayList<Client> clients = clientDAO.list();

        Assert.assertNotNull(clients);
    }

    @Test
    public void delete() throws SQLException {
        ArrayList<Client> clients = clientDAO.list();

        int sizeBeforeDelete = clients.size() - 1;

        Client foundClient = clientDAO.getLastClient();

        clientDAO.delete(foundClient.getId());

        Assert.assertEquals(sizeBeforeDelete, clientDAO.list().size());
    }
}
