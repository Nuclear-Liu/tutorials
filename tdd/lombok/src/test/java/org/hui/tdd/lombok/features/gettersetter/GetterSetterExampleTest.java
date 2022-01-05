package org.hui.tdd.lombok.features.gettersetter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Hui.Liu
 * @since 2021-12-22 20:56
 */
public class GetterSetterExampleTest {
    @Test
    public void testBaseGetterSetter() {
        GetterSetterExample subGS = new GetterSetterExample() {
            @Override
            protected GetterSetterExample setName(String name) {
                super.setName(name);
                return this;
            }
        };
        subGS.setName("LittleHui");
        subGS.setAge(25);
        assertEquals(subGS.getAge(), 25);
        assertEquals(subGS.toString(), "LittleHui (age: 25)");
    }
}