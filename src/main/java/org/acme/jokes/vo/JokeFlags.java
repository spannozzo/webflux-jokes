package org.acme.jokes.vo;

public record JokeFlags(boolean nsfw, boolean religious,
                        boolean political, boolean racist,
                        boolean sexist, boolean explicit) {
}
