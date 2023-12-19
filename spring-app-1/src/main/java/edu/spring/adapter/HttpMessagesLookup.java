package edu.spring.adapter;

import edu.spring.domain.Message;
import edu.spring.domain.MessagesLookup;
import io.micrometer.observation.ObservationRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class HttpMessagesLookup implements MessagesLookup {

    public final RestClient client;

    public HttpMessagesLookup(RestClient.Builder restClientBuilder, ObservationRegistry observationRegistry) {
        this.client = restClientBuilder
                .baseUrl("http://localhost:8888/api/v1/lookup")
                .observationRegistry(observationRegistry)
                .build();
    }


    @Override
    public List<Message> getAll() {
        log.info("Calling lookup");
        return Arrays.stream(client.get()
                .retrieve()
                .body(String.class)
                .replace("[","").replace("]","").replace("\"","")
                .split(","))
                .map(Message::new)
                .toList();
    }
}
