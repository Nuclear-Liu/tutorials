package org.hui.design.patterns.interpreter.pattern;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String expStr = "a+b-c+d";
        Map<Character, Integer> var = new HashMap<>();
        var.put('a', 5);
        var.put('b', 2);
        var.put('c', 1);
        var.put('d', 6);

        Expression expression = Analyse.analyse(expStr);
        int result = expression.interpreter(var);

        System.out.println(result);
    }
}
