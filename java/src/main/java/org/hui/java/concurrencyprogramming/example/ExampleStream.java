package org.hui.java.concurrencyprogramming.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExampleStream {
    public static final Logger LOGGER = LoggerFactory.getLogger(ExampleStream.class);

    public static List<Persion> makeList() {
        List<Persion> persions = new ArrayList<>();
        persions.add(new Persion.Builder().age(10).name("zlx").build());
        persions.add(new Persion.Builder().age(12).name("jiaduo").build());
        persions.add(new Persion.Builder().age(5).name("ruoren").build());
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
        LOGGER.info("------");
        useStream(persions);
    }
}

class Persion {
    private Integer age;
    private String name;

    private Persion(Builder builder) {
        setAge(builder.age);
        setName(builder.name);
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final class Builder {
        private Integer age;
        private String name;

        public Builder() {
        }

        public Builder age(Integer val) {
            age = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Persion build() {
            return new Persion(this);
        }
    }
}
