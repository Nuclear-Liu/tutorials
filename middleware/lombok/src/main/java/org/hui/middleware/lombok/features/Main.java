package org.hui.middleware.lombok.features;

import lombok.extern.java.Log;

/**
 * @author Hui.Liu
 * @since 2021-12-14 12:45
 */
public class Main {
    public static void main(String[] args) {
        Person person = new Person();
        person.setAge(10);
        person.setName("LittleHui");
        System.out.println(person);
        person.t();
    }
}
