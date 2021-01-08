package com.codedifferently.database;

import com.codedifferently.database.exceptions.DataBaseSaveException;
import com.codedifferently.models.person.Person;

import java.util.*;


public interface DataBase {
    void savePerson(Person person) throws DataBaseSaveException;
    void saveAllPeople(List<Person> people) throws DataBaseSaveException;
    List<Person> getAllPeople();
}
