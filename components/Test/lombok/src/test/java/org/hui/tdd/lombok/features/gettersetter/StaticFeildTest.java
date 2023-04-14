package org.hui.tdd.lombok.features.gettersetter;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Hui.Liu
 * @since 2021-12-22 22:55
 */
public class StaticFeildTest {
    @Test
    @Ignore("@Getter and @Setter can not use in static field.")
    public void testStaticFieldSetterGetter() {
        // StaticFeild.getStaticFiledOne();
    }
}