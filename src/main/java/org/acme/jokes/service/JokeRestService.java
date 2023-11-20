package org.acme.jokes.service;

import org.acme.jokes.vo.Joke;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface JokeRestService {
    Mono<Joke> getShortestSafeJoke();
}
