package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTestSuite {
    @Autowired
    private TrelloMapper trelloMapper;
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testMapToBoards() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "testTrelloListDto", true);
        List<TrelloListDto> listOfListDto = new ArrayList<>();
        listOfListDto.add(trelloListDto);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "testBoardDto", listOfListDto);
        List<TrelloBoardDto> listofBoardDto = new ArrayList<>();
        listofBoardDto.add(trelloBoardDto);
        //When
        List<TrelloBoard> listOfBoard = trelloMapper.mapToBoards(listofBoardDto);
        //Then
        Assert.assertEquals(1, listOfBoard.size());
        Assert.assertEquals("testBoardDto", listOfBoard.get(0).getName());
        Assert.assertEquals(true, listOfBoard.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        TrelloList trelloList = new TrelloList("1", "testTrelloList", true);
        List<TrelloList> listOfList = new ArrayList<>();
        listOfList.add(trelloList);
        TrelloBoard trelloBoard = new TrelloBoard("1", "testBoard", listOfList);
        List<TrelloBoard> listOfBoard = new ArrayList<>();
        listOfBoard.add(trelloBoard);
        //When
        List<TrelloBoardDto> listOfBoardDto = trelloMapper.mapToBoardsDto(listOfBoard);
        //Then
        Assert.assertEquals(1, listOfBoardDto.size());
        Assert.assertEquals("testBoard", listOfBoardDto.get(0).getName());
        Assert.assertEquals(true, listOfBoardDto.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void testMapToList() {
        //Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "testTrelloListDto", true);
        List<TrelloListDto> listOfListDto = new ArrayList<>();
        listOfListDto.add(trelloListDto);
        //When
        List<TrelloList> listOfList = trelloMapper.mapToList(listOfListDto);
        //Then
        Assert.assertEquals(1, listOfList.size());
        Assert.assertEquals("testTrelloListDto", listOfList.get(0).getName());
        Assert.assertEquals(true, listOfList.get(0).isClosed());
    }

    @Test
    public void testMapToListDto() {
        //Given
        TrelloList trelloList = new TrelloList("1", "testTrelloList", true);
        List<TrelloList> listOfList = new ArrayList<>();
        listOfList.add(trelloList);
        //When
        List<TrelloListDto> listOfListDto = trelloMapper.mapToListDto(listOfList);
        //Then
        Assert.assertEquals(1, listOfListDto.size());
        Assert.assertEquals("testTrelloList", listOfListDto.get(0).getName());
        Assert.assertEquals(true, listOfListDto.get(0).isClosed());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name1", "description1", "position1", "list1");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertEquals("name1", trelloCard.getName());
        Assert.assertEquals("description1", trelloCard.getDescription());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("name1", "description1", "position1", "list1");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertEquals("name1", trelloCardDto.getName());
        Assert.assertEquals("description1", trelloCardDto.getDescription());
    }

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "name1", "description1");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        Assert.assertEquals(1L, (long)task.getId());
        Assert.assertEquals("name1", task.getTitle());
        Assert.assertEquals("description1", task.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        Task task = new Task(1L, "name1", "description1");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        Assert.assertEquals(1L, (long)task.getId());
        Assert.assertEquals("name1", task.getTitle());
        Assert.assertEquals("description1", task.getContent());
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        Task task1 = new Task(1L, "name1", "description1");
        List<Task> listOfTask = new ArrayList<>();
        listOfTask.add(task1);

        //When
        List<TaskDto> listOfTaskDto = taskMapper.mapToTaskDtoList(listOfTask);
        //Then
        Assert.assertEquals(1, listOfTaskDto.size());
        Assert.assertEquals("name1", listOfTaskDto.get(0).getTitle());
    }

}
