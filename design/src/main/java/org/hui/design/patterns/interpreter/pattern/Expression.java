package org.hui.design.patterns.interpreter.pattern;

import java.util.Map;

public interface Expression {
    public int interpreter(Map<Character, Integer> var);
}
