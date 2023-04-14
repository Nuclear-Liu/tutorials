package org.hui.tdd.lombok.features;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

/**
 * @author Hui.Liu
 * @since 2021-12-14 12:42
 */
@Setter
@Getter
@ToString
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {
    private Long id;
    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private Integer age;
    public void t() {
        System.out.println(toString());
    }
}
