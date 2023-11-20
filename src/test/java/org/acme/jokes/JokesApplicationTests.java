package org.acme.jokes;

import org.acme.jokes.controller.JokeController;
import org.acme.jokes.service.JokeRestService;
import org.acme.jokes.service.JokeService;
import org.acme.jokes.service.RandomService;
import org.acme.jokes.vo.Joke;
import org.acme.jokes.vo.JokeApiResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class JokesApplicationTests {
    private static WebClient mockWebClient;
    private static WebClient.ResponseSpec responseSpecMock;
    private static List<Joke> unsafeJokes;
    private static List<Joke> jokes;


    @BeforeAll
    static void initTestInput() {
        initMockedWebClient();
        initJokes();
        initUnsafeJokes();
    }

    private static void initMockedWebClient() {
        mockWebClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(mockWebClient.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
    }

    private static void initJokes() {
        Joke j1 = new Joke(1, "aaa", null, true);
        Joke j2 = new Joke(2, "bb", null, true);
        Joke j3 = new Joke(3, "c", null, false);

        jokes = List.of(j1, j2, j3);
    }

    private static void initUnsafeJokes() {
        Joke j1 = new Joke(1, "aaa", null, false);
        Joke j2 = new Joke(2, "bb", null, false);
        Joke j3 = new Joke(3, "c", null, false);

        unsafeJokes = List.of(j1, j2, j3);
    }

    @Test
    void api_should_work_when_joke_service_is_failing() {

        JokeRestService stubbedServiceError = () -> Mono.error(new RuntimeException("Error occurred"));
        JokeController controller = new JokeController(stubbedServiceError, new RandomService());

        StepVerifier.create(controller.getJoke())
                .assertNext(jokeDTO -> {
                    assertTrue(jokeDTO.id() > 0);
                    assertNotNull(jokeDTO.randomJoke());
                    assertFalse(jokeDTO.randomJoke().isEmpty());
                })
                .expectComplete()
                .verify();

    }

    @Test
    void api_should_work_when_joke_service_return_empty() {

        JokeRestService dummyServiceNotFound = Mono::empty;
        JokeController controller = new JokeController(dummyServiceNotFound, new RandomService());

        StepVerifier.create(controller.getJoke())
                .assertNext(jokeDTO -> {
                    assertTrue(jokeDTO.id() > 0);
                    assertNotNull(jokeDTO.randomJoke());
                    assertFalse(jokeDTO.randomJoke().isEmpty());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void api_should_filter_unsafes_and_return_the_shortest_safe_joke_when_data_are_retrieved() {
        when(responseSpecMock.bodyToMono(JokeApiResponse.class)).thenAnswer(invocation -> {
            return Mono.just(new JokeApiResponse(jokes));
        });
        JokeController controller = new JokeController(new JokeService(mockWebClient), new RandomService());

        StepVerifier.create(controller.getJoke())
                .assertNext(jokeDTO -> {
                    assertEquals(2, jokeDTO.id());
                    assertNotNull(jokeDTO.randomJoke());
                    assertEquals("bb", jokeDTO.randomJoke());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void api_should_return_empty_when_none_are_safe_and_then_a_random_joke() {
        when(responseSpecMock.bodyToMono(JokeApiResponse.class)).thenAnswer(invocation -> {
            return Mono.just(new JokeApiResponse(unsafeJokes));
        });
        JokeController controller = new JokeController(new JokeService(mockWebClient), new RandomService());

        StepVerifier.create(controller.getJoke())
                .assertNext(jokeDTO -> {
                    assertTrue(jokeDTO.id() > 0);
                    assertNotNull(jokeDTO.randomJoke());
                    assertFalse(jokeDTO.randomJoke().isEmpty());

                    assertTrue(jokes.stream().map(Joke::id).noneMatch(id -> id == jokeDTO.id()));

                })
                .expectComplete()
                .verify();
    }

}
