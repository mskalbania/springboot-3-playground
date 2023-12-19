package edu.spring.domain;

import edu.spring.domain.utils.Async;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessagesService {

    private final ObservationRegistry observationRegistry;
    private final MessagesLookup messagesLookup;

    @SneakyThrows
    public List<Message> getAll() {
        var result = Async.withObservation("async-business-logic", observationRegistry, this::someAsyncBusinessLogic);
        Observation.createNotStarted("heavy-business-logic", observationRegistry)
                .observe(this::someHeavyBusinessLogic);
        result.get();
        return messagesLookup.getAll();
    }

    @SneakyThrows
    private void someHeavyBusinessLogic() {
        log.info("Doing heavy work");
        Thread.sleep(1000);
    }

    @SneakyThrows
    private String someAsyncBusinessLogic() {
        log.info("Doing async work");
        Thread.sleep(100);
        return "result";
    }
}
