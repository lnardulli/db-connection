package com.database.connectdb.service;

import com.database.connectdb.model.Animal;
import com.database.connectdb.model.dao.impl.AnimalDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.NoResultException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AnimalServiceTest {

    AnimalService animalService;
    @Mock
    AnimalDaoImpl animalDaoMock;

    final static String animalName = "Lion";
    final static Integer animalId = 100;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        animalService = new AnimalService(animalDaoMock);
    }

    @Test
    public void givenAnimalWhenInsertThenResultCreateOk() throws SQLException {
        final Animal animal = Animal.builder().name(animalName).build();
        when(animalDaoMock.insert(isA(Animal.class))).thenReturn(animalId);

        Integer id = animalService.create(animal);
        assertThat(id).isEqualTo(animalId);
    }

    @Test
    public void givenIdWhenFindAnimalThenReturnAnAnimal() throws SQLException {
        final Animal animal = Animal.builder().id(animalId).name(animalName).build();
        when(animalDaoMock.findById(isA(Integer.class))).thenReturn(animal);

        Animal id = animalService.getAnimalById(animal.getId());
        verify(animalDaoMock).findById(animal.getId());

        assertAnimalField(animal);

    }

    @Test
    public void givenIdWhenFindAnimalThenReturnSQLException() {
        Exception exception = assertThrows(
                NoResultException.class,
                () -> animalService.getAnimalById(animalId));

        assertEquals("Not result with this id", exception.getMessage());

    }

    @Test
    public void shouldCreateNewAnimalUsingCapture() throws SQLException {

        final Animal animal = Animal.builder().id(animalId).name(animalName).build();

        when(animalDaoMock.insert(isA(Animal.class))).thenReturn(animalId);
        ArgumentCaptor<Animal> animalArgumentCaptor = ArgumentCaptor.forClass(Animal.class);

        animalService.create(animal);
        verify(animalDaoMock).insert(animalArgumentCaptor.capture());

        assertThat(animalArgumentCaptor.getValue().getId()).isEqualTo(animalId);
    }

    private void assertAnimalField(Animal animal) {
        assertEquals(animalName, animal.getName());
        assertEquals(animalId, animal.getId());
    }

}