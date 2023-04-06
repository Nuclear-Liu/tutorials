package org.hui;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
        System.out.println(seq1);

        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);

        Mono<String> noData = Mono.empty();
        Mono<String> data = Mono.just("foo");
        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
    }
}