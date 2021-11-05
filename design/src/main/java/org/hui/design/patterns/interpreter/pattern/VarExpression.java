package org.hui.design.patterns.interpreter.pattern;

import java.util.Map;

/**
 * 变量表达式.
 */
public class VarExpression implements Expression {
    private char key;

    public VarExpression(char key) {
        this.key = key;
    }

    @Override
    public int interpreter(Map<Character, Integer> var) {
        return var.get(key);
    }
}
