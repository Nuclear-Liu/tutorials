package org.hui.tdd.junit4;

/**
 * @author Hui.Liu
 * @since 2021-11-23 21:16
 */
public class B extends A{
    public static String nameB;

}

class Test{
    public static void main(String[] args) {
        A.nameA = "A.class";
        B b = new B();
        System.out.println(b.nameA);
    }
}
