package com.codedifferently.models.addressbook;


import com.codedifferently.database.DataBase;
import com.codedifferently.database.exceptions.DataBaseSaveException;
import com.codedifferently.models.addressbook.exceptions.AddressBookPersonNotFoundException;
import com.codedifferently.models.person.Person;


import java.util.List;
import java.util.logging.Logger;

public class AddressBook {
    private static final Logger logger = Logger.getGlobal();
    private Person owner;
    private List<Person> people;
    private DataBase dataBase;

    public AddressBook(Person owner, DataBase database){
        this.owner = owner;
        this.dataBase = database;
        this.people = database.getAllPeople();
    }

    public void addPerson(Person person){
        logger.info("Adding new person " + person.getFirstName());
        this.people.add(person);
        saveAll();
    }

    public void removePerson(Person person){
        logger.info("Removing person " + person.getFirstName());
        this.people.remove(person);
        /**
         * Todo :: update database;
         */
    }

    public Person getPersonByEmail(String email) throws AddressBookPersonNotFoundException {
        /**
         * Todo :: get update
         */
        for (Person person:people) {
            if(person.getEmail().equals(email)){
                return person;
            }
        }
        throw new AddressBookPersonNotFoundException();
    }

    public List<Person> getAllPeople(){
        return people;
    }

    public Boolean saveAll(){
        try {
            dataBase.saveAllPeople(people);
            return true;
        } catch (DataBaseSaveException e) {
            return false;
        }

    }
}

