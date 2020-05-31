package org.afpa.dal.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CRUD<T> {
    void delete(int id) throws SQLException;

    T find(int id) throws SQLException;

    void insert(T object) throws SQLException;

    ArrayList<T> list() throws SQLException;

    void update(T object) throws SQLException;
}

