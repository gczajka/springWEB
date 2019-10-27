package com.crud.tasks.trello.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloConfigTest {
    @Autowired
    TrelloConfig trelloConfig;

    @Test
    public void getAPIEndpoint() {
        //Given
        //When
        String endpoint = trelloConfig.getTrelloApiEndpoint();

        //Then
        Assert.assertEquals("https://api.trello.com/1", endpoint);
    }

    @Test
    public void getKey() {
        //Given
        //When
        String key = trelloConfig.getTrelloAppKey();

        //Then
        Assert.assertEquals("c7c36e6f4bae13d1876a1dc9bdcfacdc", key);
    }

    @Test
    public void getToken() {
        //Given
        //When
        String token = trelloConfig.getTrelloToken();

        //Then
        Assert.assertEquals("4a01a34e10c288358245e0132ced69d750a35ba6ee3b4448d95228f96bdd03a4", token);
    }

    @Test
    public void getUsername() {
        //Given
        //When
        String username = trelloConfig.getTrelloUsername();

        //Then
        Assert.assertEquals("grzegorzczajka", username);
    }
}
