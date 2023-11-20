package org.acme.jokes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.acme.jokes.dto.JokeDTO;
import org.acme.jokes.service.JokeRestService;
import org.acme.jokes.service.RandomService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/jokes")
public class JokeController {
    private final JokeRestService jokeService;
    private final RandomService randomService;

    public JokeController(JokeRestService jokeService, RandomService randomService) {
        this.jokeService = jokeService;
        this.randomService = randomService;
    }

    @GetMapping(value = "/safe", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get a random joke", description = "Fetches a random, safe-for-work joke")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of joke",
            content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = JokeDTO.class)) })
    public Mono<JokeDTO> getJoke() {
        return jokeService.getShortestSafeJoke()
                .switchIfEmpty(Mono.error(new RuntimeException("jokes not found")))
                .map(joke -> new JokeDTO(joke.id(), joke.joke()))
                .onErrorResume(throwable -> Mono.just(getRandomJoke()));
    }

    private JokeDTO getRandomJoke() {
        var randomId = randomService.getRandomInt(250);
        String randomJoke = randomService.getRandom(JokeDTO.FALLBACK_JOKES);

        return new JokeDTO(randomId, randomJoke);
    }

}
