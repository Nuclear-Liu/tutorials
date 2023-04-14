package org.hui.tdd.junit4;

import org.junit.Rule;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Hui.Liu
 * @since 2021-11-19 13:07
 */
public class IdentityRule implements TestRule {
    @Override
    public Statement apply(Statement base, Description description) {
        return base;
    }
}
