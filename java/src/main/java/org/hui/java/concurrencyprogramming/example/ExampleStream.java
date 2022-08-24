package org.hui.java.concurrencyprogramming.example;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExampleStream {
    public static List<Persion> makeList() {
        List<Persion> persions = new ArrayList<>();
        persions.add(Persion.builder().age(10).name("zlx").build());
        persions.add(Persion.builder().age(12).name("jiaduo").build());
        persions.add(Persion.builder().age(5).name("ruoren").build());
        return persions;
    }
    public static void noStream(List<Persion> persions) {
        List<String> nasmes = new ArrayList<>();

        for (Persion persion : persions) {
            if (persion.getAge() >= 10) {
                nasmes.add(persion.getName());
            }
        }

        nasmes.forEach(System.out::println);
    }
    public static void useStream(List<Persion> persions) {
        // List<String> names = persions.stream().filter(persion -> persion.getAge() >= 10).map(persion -> persion.getName()).collect(Collectors.toList());
        // names.forEach(System.out::println);
        persions.stream().filter(persion -> persion.getAge() >= 10).map(persion -> persion.getName()).forEach(System.out::println);
    }

    public static void main(String[] args) {
        List<Persion> persions = makeList();
        noStream(persions);
        log.info("------");
        useStream(persions);
    }
}

@Setter
@Getter
@Builder
class Persion {
    private Integer age;
    private String name;
}
