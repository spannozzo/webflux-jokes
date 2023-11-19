package org.acme.jokes.vo;

import java.util.List;

public record JokeApiResponse(List<Joke> jokes) {
}
