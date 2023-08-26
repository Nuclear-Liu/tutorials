package org.hui.mybatis.log;

import org.apache.ibatis.logging.Log;

/**
 * 自定义 Logger 实现.
 */
public class CustomLogger implements Log {
    public CustomLogger(String clazz) {
        // do nothing.
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        System.err.println(String.format("custom logger: %s" , s));
        e.printStackTrace(System.err);
    }

    @Override
    public void error(String s) {
        System.err.println(String.format("custom logger: %s" , s));
    }

    @Override
    public void debug(String s) {
        System.out.println(String.format("custom logger: %s" , s));
    }

    @Override
    public void trace(String s) {
        System.out.println(String.format("custom logger: %s" , s));
    }

    @Override
    public void warn(String s) {
        System.out.println(String.format("custom logger: %s" , s));
    }
}
