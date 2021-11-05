package org.hui.design.patterns.interpreter.pattern;

import java.util.Stack;

public class Analyse {
    public static Expression analyse(String expStr) {
        Stack<Expression> expStack = new Stack<>();
        Expression left = null;
        Expression right = null;
        for (int i = 0; i < expStr.length(); i++) {
            switch (expStr.charAt(i)) {
                case '+' : // 加法运算
                    left = expStack.pop();
                    right = new VarExpression(expStr.charAt(++i));
                    expStack.push(new AddExpression(left, right));
                    break;
                case '-' : // 减法运算
                    left = expStack.pop();
                    right = new VarExpression(expStr.charAt(++i));
                    expStack.push(new SubExpression(left, right));
                    break;
                default: // 变量表达式
                    expStack.push(new VarExpression(expStr.charAt(i)));
            }
        }
        Expression expression = expStack.pop();
        return expression;
    }
}
