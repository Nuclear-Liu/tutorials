package org.hui.tdd.lombok.features;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * How to Write an Equality Method in Java.
 * Test Case.
 *
 * @author Hui.Liu
 * @since 2021-12-17 21:04
 */
public class EqualsTest {
    @Test
    // @Ignore("重载了 equals 方法，而不是重写。容器调用的任然是父类的默认 equals 方法。")
    public void testEqualsInSetFail() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        assertTrue(p1.equals(p2));
        Set<Point> pSet = new HashSet<>();
        pSet.add(p1);
        assertTrue(pSet.contains(p2));
    }

    @Test
    // @Ignore("调用默认 hashCode方法，地址的转换值，偶尔当hash出现碰撞的时候会出现相等的情况，调用默认的父类 equals 方法，比较对象的地址。")
    public void testEqualsFail() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);
        assertTrue(p1.equals(p2));
        Object p2a = p2;
        assertTrue(p1.equals(p2a));
    }

    @Test
    @Ignore("当已经存放到集合中的对象改变时，计算出来的哈希桶并不是实际开始存放对象所在的哈希桶。")
    public void testNoFinalField() {
        Point p = new Point(1, 2);

        HashSet<Point> coll = new HashSet<>();
        coll.add(p);

        assertTrue(coll.contains(p)); // succeed

        p.setX(p.getX() + 1);

        Iterator<Point> it = coll.iterator();
        boolean containedP = false;
        while (it.hasNext()) {
            Point nextP = it.next();
            if (nextP.equals(p)) {
                containedP = true;
                break;
            }
        }
        assertTrue(containedP);

        assertTrue(coll.contains(p));
    }

    @Test
    public void testSubclass() {
        Point p = new Point(1, 2);

        ColoredPoint cp = new ColoredPoint(1, 2, Color.RED);

        // assertTrue(p.equals(cp)); // the result true
        assertFalse(p.equals(cp)); // the result true

        // assertTrue(cp.equals(p)); // the result false
        assertFalse(cp.equals(p)); // the result false
    }

    @Test
    public void testSubclassOnCollection() {
        Point p = new Point(1, 2);

        ColoredPoint cp = new ColoredPoint(1, 2, Color.RED);

        Set<Point> hashSet1 = new HashSet<>();
        hashSet1.add(p);
        // assertTrue(hashSet1.contains(cp)); // assert result false; subclass equals superclass
        assertFalse(hashSet1.contains(cp)); // 严格模式

        Set<Point> hashSet2 = new HashSet<>();
        hashSet2.add(cp);
        // assertTrue(hashSet2.contains(p)); // assert result true; superclass equals subclass
        assertFalse(hashSet2.contains(p)); // 严格模式
    }

    @Test
    @Ignore("测试 hash 默认 jdk 实现的碰撞 暂时没有遇到碰撞的情况。")
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

    @Test
    public void testAnonymousSubClass() {
        Point p = new Point(1, 2);
        ColoredPoint cp = new ColoredPoint(1, 2, Color.INDIGO);
        Point pAnon = new Point(1, 1) {
            @Override
            public int getY() {
                return 2;
            }
        };
        assertTrue(p.equals(pAnon));

        Set<Point> coll = new HashSet<>();
        coll.add(p);

        assertTrue(coll.contains(p));
        assertFalse(coll.contains(cp));
        assertTrue(coll.contains(pAnon));
    }
}
