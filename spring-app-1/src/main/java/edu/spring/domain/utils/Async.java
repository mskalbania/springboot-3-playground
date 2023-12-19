package edu.spring.domain.utils;

import io.micrometer.context.ContextExecutorService;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class Async {

    private static final ExecutorService virtualThreadContextAwareThreadExecutor =
            ContextExecutorService.wrap(Executors.newVirtualThreadPerTaskExecutor());

    public static <T> Future<T> withObservation(String actionName, ObservationRegistry registry, Supplier<T> function) {
        return virtualThreadContextAwareThreadExecutor.submit(() ->
                Observation.createNotStarted(actionName, registry).observe(function));
    }
}
