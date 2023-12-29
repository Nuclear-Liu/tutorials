package org.hui.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * 自定义拦截器
 */
@Intercepts({
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(
                type = Executor.class,
                method = "close",
                args = {boolean.class}
        )})
public class MyInterceptor implements Interceptor {
    private static final Logger LOGGER = LogManager.getLogger();
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        LOGGER.info("----------- My Interceptor Before.");
        Object proceed = invocation.proceed();
        LOGGER.info("----------- My Interceptor After.");
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        LOGGER.info("properties value: {}", properties.getProperty("value"));
        value = properties.getProperty("value");
    }
}
