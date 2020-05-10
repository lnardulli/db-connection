package com.database.connectdb.model.dao;

import com.database.connectdb.model.Animal;

import java.sql.SQLException;

public interface AnimalDao {

    Integer insert(Animal animal);
    Animal findById(Integer id) throws SQLException;

}
