package org.hui.patterns.singleton.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hui.patterns.singleton.PrototypeService;
import org.hui.patterns.singleton.SingletonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private static final Logger LOGGER = LogManager.getLogger();

    private final SingletonService singleton;

    private final PrototypeService prototype;

    public Controller(SingletonService singleton, PrototypeService prototype) {
        this.singleton = singleton;
        this.prototype = prototype;
    }

    @GetMapping("singleton/{msg}")
    public String singleton(@PathVariable String msg) {
        LOGGER.info("instance: {}", singleton);
        return singleton.echo(msg);
    }
    @GetMapping("prototype/{msg}")
    public String prototype(@PathVariable String msg) {
        LOGGER.info("instance: {}", prototype);
        return singleton.echo(msg);
    }

}
