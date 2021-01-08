package com.codedifferently.models.person;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PersonTest {
    @Test
    public void constructorTest01(){
        Map<String, String> rawData = new HashMap<>();
        rawData.put("firstName", "Holly");
        rawData.put("lastName", "Chaffe");
        rawData.put("email", "holly@gotbangangs.com");
        rawData.put("age", "25");
        Person person = new Person(rawData);
        String expected = "Holly Chaffe holly@gotbangangs.com 25";
        String actual = person.toString();
        Assert.assertEquals("Create a Person with no id",expected, actual);
    }

    @Test
    public void constructorTest02(){
        Map<String, String> rawData = new HashMap<>();
        String id = UUID.randomUUID().toString();
        rawData.put("firstName", "Aakriti");
        rawData.put("lastName", "Shrestha");
        rawData.put("email", "aakriti@superawesome.com");
        rawData.put("age", "21");
        Person person = new Person(id,rawData);
        String expected = id;
        String actual = person.getId();
        Assert.assertEquals("Create a Person with id",expected, actual);
    }
}
