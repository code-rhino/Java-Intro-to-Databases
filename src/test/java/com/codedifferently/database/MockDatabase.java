package com.codedifferently.database;

import com.codedifferently.database.exceptions.DataBaseSaveException;
import com.codedifferently.models.person.Person;

import java.util.List;

public class MockDatabase implements DataBase{
    @Override
    public void savePerson(Person person) throws DataBaseSaveException {

    }

    @Override
    public void saveAllPeople(List<Person> people) throws DataBaseSaveException {

    }

    @Override
    public List<Person> getAllPeople() {
        return null;
    }
}
