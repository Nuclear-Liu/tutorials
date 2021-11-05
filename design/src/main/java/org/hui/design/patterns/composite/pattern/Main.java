package org.hui.design.patterns.composite.pattern;

public class Main {

    public static void invoke(Component component) {
        // ...
        component.process();
        // ...
    }
    public static void main(String[] args) {
        Composite root = new Composite("root", null);
        Composite treeNode1 = new Composite("treeNode1", null);
        Composite treeNode2 = new Composite("treeNode2", null);
        Composite treeNode3 = new Composite("treeNode3", null);
        Composite treeNode4 = new Composite("treeNode4", null);
        Leaf leaf1 = new Leaf("leaf1");
        Leaf leaf2 = new Leaf("leaf2");
        root.add(treeNode1);
        treeNode1.add(treeNode2);
        treeNode2.add(leaf2);

        root.add(treeNode3);
        treeNode3.add(treeNode4);
        treeNode4.add(leaf2);

        invoke(root);
    }
}
