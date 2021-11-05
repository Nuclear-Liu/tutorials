package org.hui.design.patterns.decorator.pattern;

public abstract class DecoratorStream implements Stream {
    protected Stream stream;

    protected DecoratorStream(Stream stream) {
        this.stream = stream;
    }

}
