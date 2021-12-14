package org.hui.middleware.lombok.features;

import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

/**
 * @author Hui.Liu
 * @since 2021-12-14 12:42
 */
@Setter
@ToString
@Log
public class Person {
    private String name;
    private Integer age;
    public void t() {
        myLog.info("before into t()");
        myLog.info("after out t()");
    }
}
