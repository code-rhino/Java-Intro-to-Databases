package com.codedifferently.models.addressbook;

import com.codedifferently.database.DataBase;
import com.codedifferently.database.MockDatabase;
import org.junit.Test;

public class AddressBookTest {

    @Test
    public void constructorTest(){
        DataBase dataBase = new MockDatabase();
        AddressBook addressBook = new AddressBook(null, dataBase);
    }
}
