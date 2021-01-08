package com.codedifferently.database;

import com.codedifferently.database.exceptions.DataBaseConnectionException;
import com.codedifferently.database.exceptions.DataBaseSaveException;
import com.codedifferently.models.person.Person;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class MySQLDataBase implements DataBase{
    private static final Logger logger = Logger.getGlobal();
    private Connection connection;

    private void getConnection() throws DataBaseConnectionException {
        try{
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/addressbook?createDatabaseIfNotExist=true&useSSL=false", "developer01", "pass");
            connection.setAutoCommit(true);
            logger.info("Successful Connection");
        }catch (Exception e) {
            logger.warning(e.getMessage());
            throw new DataBaseConnectionException();
        }
    }

    public void savePerson(Person person) throws DataBaseSaveException {
        try {
            String sqlInsert = String.format("INSERT INTO PERSON (id,first_name,last_name,email,age) VALUES ('%s','%s','%s','%s','%d')",
                    person.getId(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getEmail(),
                    person.getAge());
            Statement statement = connection.createStatement();
            statement.execute(sqlInsert);
        } catch (SQLException ex){
            throw new DataBaseSaveException();
        }
    }

    public void saveAllPeople(List<Person> people) throws DataBaseSaveException {
        try {
            String sqlInsert = "INSERT INTO PERSON(id,first_name,last_name,email,age) VALUES (?,?,?,?,?)";
            PreparedStatement peopleStmt = connection.prepareStatement(sqlInsert);
            for (Person person : people) {
                peopleStmt.setString(1, person.getId());
                peopleStmt.setString(2, person.getFirstName());
                peopleStmt.setString(3, person.getLastName());
                peopleStmt.setString(4, person.getEmail());
                peopleStmt.setInt(5, person.getAge());
                peopleStmt.addBatch();
                logger.info("Saving " + person.getFirstName());
            }
            peopleStmt.executeBatch();
            connection.commit();
        }catch (Exception e){
            throw new DataBaseSaveException();
        }
    }

    public List<Person> getAllPeople(){
        logger.info("Getting All Contacts");
        List<Person> people = new ArrayList<>();
        try {
            String sql = "SELECT * FROM PERSON";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                Map<String, String> rawData = new HashMap<>();
                String id = resultSet.getString("id");
                rawData.put("firstName", resultSet.getString("first_name"));
                rawData.put("lastName", resultSet.getString("last_name"));
                rawData.put("email", resultSet.getString("email"));
                /**
                 * Todo: Check to make sure parse for int is correct
                 */
                rawData.put("age",resultSet.getString("age"));
                Person person = new Person(id,rawData);
                people.add(person);
            }
        } catch (SQLException ex){
            logger.info(ex.getMessage());
        }
        return people;
    }

    public MySQLDataBase() throws DataBaseConnectionException {
        getConnection();
    }
}
