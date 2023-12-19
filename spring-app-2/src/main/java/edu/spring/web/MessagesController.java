package edu.spring.web;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lookup")
@RequiredArgsConstructor
@Slf4j
public class MessagesController {

    private final ObservationRegistry observationRegistry;

    @GetMapping
    public ResponseEntity<List<String>> getAll() {
        log.info("Incoming request GET /api/v1/lookup");
        Observation.createNotStarted("other-business-logic", observationRegistry)
                .observe(this::otherBusinessLogic);
        return ResponseEntity.ok(List.of("msg-1", "msg-2"));
    }

    @SneakyThrows
    private void otherBusinessLogic() {
        Thread.sleep(500);
    }
}
