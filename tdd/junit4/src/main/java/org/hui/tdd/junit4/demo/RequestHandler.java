package org.hui.tdd.junit4.demo;

/**
 * process request return response
 * @author Hui.Liu
 * @since 2021-11-28 0:35
 */
public interface RequestHandler {
    /**
     * process request return response
     */
    Response process(Request request) throws Exception;
}
