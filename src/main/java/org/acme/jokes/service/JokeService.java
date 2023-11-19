package org.acme.jokes.service;

import org.acme.jokes.vo.Joke;
import org.acme.jokes.vo.JokeApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class JokeService {
    private final WebClient webClient;

    @Value("${jokeApiUrl}")
    private String endpointUrl;

    public JokeService() {
        this.webClient = WebClient.builder().build();
    }

    public Mono<Joke> getShortestSafeJoke() {
        return webClient.get()
                .uri(endpointUrl).retrieve()
                .bodyToMono(JokeApiResponse.class)
                .flatMapMany(response -> Flux.fromIterable(response.jokes()))
                .filter(Joke::safe)
                .reduce(((joke1, joke2) -> joke1.joke().length() < joke2.joke().length() ? joke1 : joke2))
                ;
    }
}
