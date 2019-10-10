package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.config.TrelloConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Component
public class TrelloClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    private TrelloConfig trelloConfig;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = buildURLForGet(trelloConfig.getTrelloApiEndpoint(), trelloConfig.getTrelloUsername(), trelloConfig.getTrelloAppKey(), trelloConfig.getTrelloToken());

        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(url, TrelloBoardDto[].class);
            return Arrays.asList(ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto) {
        URI url = buildURLForPost(trelloCardDto, trelloConfig.getTrelloApiEndpoint(), trelloConfig.getTrelloAppKey(), trelloConfig.getTrelloToken());
        return restTemplate.postForObject(url, null, CreatedTrelloCard.class);
    }

    private static URI buildURLForGet(String endpoint, String username, String key, String token) {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/members/" + username + "/boards")
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
    }

    private static URI buildURLForPost(TrelloCardDto dto,String endpoint, String key, String token) {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/cards")
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("name", dto.getName())
                .queryParam("desc", dto.getDescription())
                .queryParam("pos", dto.getPos())
                .queryParam("idList", dto.getListId()).build().encode().toUri();
    }
}
