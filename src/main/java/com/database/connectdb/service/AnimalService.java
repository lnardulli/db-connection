package com.database.connectdb.service;

import com.database.connectdb.model.Animal;
import com.database.connectdb.model.dao.AnimalDao;
import com.database.connectdb.model.dao.impl.AnimalDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.sql.SQLException;

@Service
public class AnimalService {

    private AnimalDao animalDao;

    @Autowired
    public AnimalService(AnimalDaoImpl animalDaoImpl) {
        this.animalDao = animalDaoImpl;
    }

    public Animal getAnimalById(Integer id) throws SQLException {
        Animal animal = animalDao.findById(id);
        if(animal == null)
            throw new NoResultException("Not result with this id");

        return animal;
    }

    public Integer create(Animal animal) {
        return animalDao.insert(animal);
    }
}
