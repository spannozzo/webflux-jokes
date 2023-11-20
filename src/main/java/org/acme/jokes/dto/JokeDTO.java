package org.acme.jokes.dto;


import java.util.List;

public record JokeDTO(int id, String randomJoke) {
    public static final List<String> FALLBACK_JOKES = List.of(
            "A man walks into a coffee: splash.",
            "Never date a baker. They're too kneady.",
            "Debugging: Removing the needles from the haystack.",
            "UDP is better in the COVID era since it avoids unnecessary handshakes.",
            "Schrödinger's cat walks into a bar and doesn't.",
            "I'm reading a book about anti-gravity. It's impossible to put down!",
            "\"We messed up the keming again guys.\"",
            "I have a joke about trickle down economics, but 99% of you will never get it.",
            "If Bill Gates had a dime for every time Windows crashed ... Oh wait, he does.",
            "ASCII silly question, get a silly ANSI."
    );
}
