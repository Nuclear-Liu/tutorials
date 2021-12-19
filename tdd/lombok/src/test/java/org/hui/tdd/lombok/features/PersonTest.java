package org.hui.tdd.lombok.features;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Hui.Liu
 * @since 2021-12-17 20:00
 */
public class PersonTest {
    @Test
    public void testEquals() {
        Person p1 = new Person.PersonBuilder().id(3L).name("Hui").age(25).build();
        Person p2 = new Person.PersonBuilder().id(4L).name("Hui").age(25).build();
        assertEquals(p1, p2);
        System.out.println(p1);
        System.out.println(p2);
        Person p3 = new Person.PersonBuilder().id(3L).name("Hui").build();
        Person p4 = new Person.PersonBuilder().id(4L).name("Hui").build();
        assertEquals(p3, p4);
        System.out.println(p3);
        System.out.println(p4);
        Person p5 = new Person.PersonBuilder().id(3L).build();
        Person p6 = new Person.PersonBuilder().id(4L).build();
        assertEquals(p5, p6);
        System.out.println(p5);
        System.out.println(p6);
        Person p7 = new Person.PersonBuilder().id(3L).name("Hui").build();
        Person p8 = new Person.PersonBuilder().id(4L).name("Hui2").build();
        assertNotEquals(p7, p8);
        System.out.println(p7);
        System.out.println(p8);
    }
}