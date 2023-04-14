package org.hui.tdd.junit4.demo;

/**
 * controller
 * @author Hui.Liu
 * @since 2021-11-28 0:36
 */
public interface Controller {
    /**
     * process request top layer
     */
    Response processRequest(Request request);

    /**
     * extend controller : add new process handler
     */
    void addHandler(Request request, RequestHandler requestHandler);
}
