package edu.spring.web;

import edu.spring.domain.Message;
import edu.spring.domain.MessagesService;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Slf4j
public class MessagesController {

    private final MessagesService messagesService;
    private final Tracer tracer;

    @GetMapping
    public ResponseEntity<List<Message>> getAll() {
        log.info("Incoming request GET /api/v1/messages");
        return ResponseEntity.status(HttpStatus.OK)
                .header("traceId", tracer.currentSpan().context().traceId())
                .body(messagesService.getAll());
    }
}
