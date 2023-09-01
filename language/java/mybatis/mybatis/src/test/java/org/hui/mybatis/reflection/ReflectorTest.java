package org.hui.mybatis.reflection;

import org.apache.ibatis.reflection.Reflector;
import org.hui.domain.City;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectorTest {
    @Test
    public void test() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflector reflector = new Reflector(City.class);
        Arrays.stream(reflector.getGetablePropertyNames()).forEach(System.out::println);
        City city = (City) reflector.getDefaultConstructor().newInstance();
        System.out.println(city);
    }
}
