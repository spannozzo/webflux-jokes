package org.acme.jokes.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class RandomService {
    private final Random random;

    public RandomService() {
        random = new Random();
    }

    public <T> T getRandom(List<T> elements) {
        var randomIndex = random.nextInt(elements.size());
        return elements.get(randomIndex);
    }

    public int getRandomInt(int maxValue) {
        return random.nextInt(maxValue);
    }
}
