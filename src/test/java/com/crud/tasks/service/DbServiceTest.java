package com.crud.tasks.service;


import com.crud.tasks.domain.Task;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DbServiceTest {
    @Autowired
    private DbService dbService;

    @Test
    public void saveTask() {
        //Given
        Task task = new Task(null, "name", "description");

        //When
        Task savedTask = dbService.saveTask(task);

        //Then
        Assert.assertEquals("name",savedTask.getTitle());

        //CleanUp
        dbService.deleteTask(savedTask.getId());
    }

    @Test
    public void getAllTasks() {
        //Given
        Task task = new Task(null, "name", "description");
        Task savedTask = dbService.saveTask(task);

        //When
        List<Task> listOfTasks = dbService.getAllTasks();

        //Then
        Assert.assertEquals(1, listOfTasks.size());

        //CleanUp
        dbService.deleteTask(savedTask.getId());
    }

    @Test
    public void getTask() {
        //Given
        Task task = new Task(null, "name2", "description2");
        Task savedTask = dbService.saveTask(task);

        //When
        Optional<Task> gotTask = dbService.getTask(savedTask.getId());

        //Then
        Assert.assertEquals("name2", gotTask.orElse(null).getTitle());

        //CleanUp
        dbService.deleteTask(savedTask.getId());
    }

    @Test
    public void deleteTask() {
        //Given
        Task task = new Task(null, "name3", "description3");
        Task savedTask = dbService.saveTask(task);

        //When
        dbService.deleteTask(savedTask.getId());
        int size = dbService.getAllTasks().size();

        //Then
        Assert.assertEquals(0, size);
    }
}
