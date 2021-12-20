package org.hui.tdd.junit4;

import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.model.Statement;

/**
 * @author Hui.Liu
 * @since 2021-11-19 11:47
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({A.class, B.class, C.class})
public class UsesExternalResource {
    @ClassRule
    public static final ExternalResource resources = new ExternalResource() {
        @Override
        public Statement apply(Statement base, Description description) {
            return super.apply(base, description);
        }

        @Override
        protected void before() throws Throwable {
            // init
            System.out.println("init");
        }

        @Override
        protected void after() {
            // destroy
            System.out.println("destroy");
        }
    };
}
