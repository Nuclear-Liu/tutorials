package org.hui.middleware.lombok.features;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Hui.Liu
 * @since 2021-12-17 21:04
 */
public class EqualsTest {
    @Test
//    @Ignore("重载了 equals 方法，而不是重写。容器调用的任然是父类的默认 equals 方法。")
    public void testEqualsInSetFail() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        assertTrue(p1.equals(p2));
        Set<Point> pSet = new HashSet<>();
        pSet.add(p1);
        assertTrue(pSet.contains(p2));
    }
    @Test
//    @Ignore("调用默认 hashCode方法，地址的转换值，偶尔当hash出现碰撞的时候会出现相等的情况，调用默认的父类 equals 方法，比较对象的地址。")
    public void testEqualsFail() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        assertTrue(p1.equals(p2));
        Object p2a = p2;
        assertTrue(p1.equals(p2a));
    }
    @Test
    @Ignore("测试hash 默认的碰撞 暂时没有遇到碰撞的情况。")
    public void test() {
        long count = 0;
        boolean flag = true;
        Set<Point> pSet = new HashSet<>();
        Point p2 = new Point(1, 1);
        while (flag) {
            Point p1 = new Point(1, 1);
            pSet.add(p1);
            flag = !pSet.contains(p2);
        }
        System.out.println(count);
        System.out.println(pSet.size());
    }
    @Test
    public void testEqualsInSetSucceed() {
        Person p1 = new Person.PersonBuilder().id(1L).name("Hui").age(25).build();
        Person p2 = new Person.PersonBuilder().id(2L).name("Hui").age(25).build();
        assertEquals(p1, p2);
        Set<Person> pSet = new HashSet<>();
        pSet.add(p1);
        assertTrue(pSet.contains(p2));
    }
}
