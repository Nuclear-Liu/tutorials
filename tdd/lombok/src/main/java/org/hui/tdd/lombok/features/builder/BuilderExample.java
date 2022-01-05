package org.hui.tdd.lombok.features.builder;

import lombok.Builder;
import lombok.Singular;

import java.util.Set;

/**
 * @author Hui.Liu
 * @since 2021-12-31 12:58
 */
@Builder
public class BuilderExample {
    @Builder.Default private long created = System.currentTimeMillis();
    private String name;
    private int age;
    @Singular private Set<String> occupations;
}
