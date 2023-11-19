package org.acme.jokes.vo;


public record Joke(int id, String joke, JokeFlags flags, boolean safe) {
}
