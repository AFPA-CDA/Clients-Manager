package org.afpa.dal.shared;

import org.afpa.dal.models.Client;

/**
 * The class used to validate models
 */
public final class Validator {
    /**
     * @param client The client to validate
     * @return {@code true} if valide else {@code false}
     */
    public static boolean validateClient(Client client) {
        String[] isRequired = {client.getCity(), client.getFirstName(), client.getLastName()};
        String[] isValidString = {client.getAddress(), client.getCity(), client.getFirstName(), client.getLastName()};

        for (String value : isRequired) {
            if (value == null || value.isEmpty()) return false;
        }

        for (String value : isValidString) {
            if (value.matches("[^\\w\\s]")) return false;
        }

        // Everything is valid return true
        return true;
    }
}
