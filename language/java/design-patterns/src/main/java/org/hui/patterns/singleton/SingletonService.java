package org.hui.patterns.singleton;

import org.springframework.stereotype.Component;

@Component
public class SingletonService {
    public String echo(String msg) {
        return msg;
    }
}
