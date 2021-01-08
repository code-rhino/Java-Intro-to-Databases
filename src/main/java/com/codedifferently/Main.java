package com.codedifferently;

import com.codedifferently.database.DataBase;
import com.codedifferently.database.MySQLDataBase;
import com.codedifferently.database.exceptions.DataBaseConnectionException;
import com.codedifferently.models.addressbook.AddressBook;
import com.codedifferently.models.addressbook.exceptions.AddressBookPersonNotFoundException;
import com.codedifferently.models.person.Person;


import java.util.*;

public class Main {

    private DataBase dataBase;
    private static Scanner scanner;
    private AddressBook addressBook;
    private ArrayList<String> menu;
    public Main() throws DataBaseConnectionException {
        dataBase = new MySQLDataBase();
        addressBook = new AddressBook(null, dataBase);
        scanner = new Scanner(System.in);
        initMenuOption();
    }

    private void initMenuOption(){
        menu = new ArrayList<>();
        menu.add("Exit");
        menu.add("Show All");
        menu.add("Add new");
        menu.add("Find by email");
    }

    public Integer displayMenu(){
        int option = 0;
        for(int i = 0; i < menu.size(); i++){
            String menuOption = String.format("Press %d for %s", i, menu.get(i));
            System.out.println(menuOption);
        }
        option = scanner.nextInt();
        return option;
    }

    public void displayAllPeople(){
        System.out.println("Here is a lists of all the people in your Address book:");
        List<Person> people =addressBook.getAllPeople();
        if(people.size() == 0){
            System.out.println("You have no one in your address book yet");
        }
        for (Person person:people) {
            displayPerson(person);
        }
    }

    private String getStringOutPut(String msg){
        System.out.println(msg);
        return scanner.next();
    }

    private Integer getIntegerOutput(String msg){
        System.out.println(msg);
        String response = scanner.next();
        /**
         * check value
         */
        return Integer.parseInt(response);
    }

    public void createNewPerson(){
        Map<String, String> rawData = new HashMap<>();
        rawData.put("firstName", getStringOutPut("Please enter first name:"));
        rawData.put("lastName", getStringOutPut("Please enter last name:"));
        rawData.put("email", getStringOutPut("Please enter email:"));
        rawData.put("age", getStringOutPut("Please enter age:"));
        Person person = new Person(rawData);
        addressBook.addPerson(person);
    }

    public void findPerson(){
        String email = getStringOutPut("What email are you looking for?");
        try {
            Person person = addressBook.getPersonByEmail(email);
            displayPerson(person);
        } catch (AddressBookPersonNotFoundException e) {
            System.out.println("There is no user with the email :" + email);
        }
    }

    private void displayPerson(Person person){
        String output = String.format("%s %s %s %s %d", person.getId(), person.getFirstName(), person.getLastName(), person.getEmail(), person.getAge());
        System.out.println(output);
    }

    public static void main(String[] args) {
        try {
            Main main = new Main();
            Boolean endProgram = false;
            System.out.println("Welcome to address book");
            while (!endProgram) {
                int menuOption = main.displayMenu();
                switch(menuOption){
                    case 0:
                        System.out.println("Goodbye!!");
                        endProgram = true;
                        break;
                    case 1:
                        main.displayAllPeople();
                        break;
                    case 2:
                        main.createNewPerson();
                        break;
                    case 3:
                        main.findPerson();
                        break;
                    default:
                        System.out.println("I don't know that command");
                        break;
                }
            }
        } catch (DataBaseConnectionException e) {
            System.out.println("Your database could not be connected to.");
        }
    }
}
