package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {
    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String trelloUsername;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {
        URI url = buildURL(trelloApiEndpoint, trelloUsername, trelloAppKey, trelloToken);
        TrelloBoardDto[] boardsResponse= restTemplate.getForObject(url, TrelloBoardDto[].class);
        Optional<List<TrelloBoardDto>> list = Optional.ofNullable(Arrays.asList(boardsResponse));
        return list.orElse(new ArrayList<>());
    }

    private static URI buildURL(String endpoint, String username, String key, String token) {
        return UriComponentsBuilder.fromHttpUrl(endpoint + "/members/" + username + "/boards")
                .queryParam("key", key)
                .queryParam("token", token)
                .queryParam("fields", "name,id").build().encode().toUri();
    }
}
