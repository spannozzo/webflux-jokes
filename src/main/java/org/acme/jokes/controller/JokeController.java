package org.acme.jokes.controller;

import org.acme.jokes.dto.JokeDTO;
import org.acme.jokes.service.JokeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class JokeController {
    private final JokeService jokeService;

    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }
    @GetMapping("/joke")
    public Mono<JokeDTO> getJoke(){
        return jokeService.getShortestSafeJoke()
                .map(joke -> new JokeDTO(joke.id(),joke.joke()));
    }

}
