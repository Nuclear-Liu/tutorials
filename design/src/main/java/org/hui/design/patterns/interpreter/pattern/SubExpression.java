package org.hui.design.patterns.interpreter.pattern;

import java.util.Map;

public class SubExpression extends SymbolExpression {
    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public int interpreter(Map<Character, Integer> var) {
        return left.interpreter(var) - right.interpreter(var);
    }
}
