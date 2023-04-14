package org.hui.java.jvm;

public class DynamicTest {
    private int i;

    public void m(int a) {
    }

    public void m(int a, int b) {
    }

    public static void main(String[] args) {
        DynamicTest dt = new DynamicTest();
        dt.m(1);
        dt.m(1, 2);
        AbstractClass ac = new SubClass();
        ac.m();
    }
}

class SubClass extends AbstractClass {

    @Override
    public void m() {
        System.out.println("call sub");
    }
}
