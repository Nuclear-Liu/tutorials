package org.hui.design.patterns.decorator.pattern;

/**
 * 流操作
 */
public interface Stream {
    char read(int number);
    void seek(int position);
    void write(char data);
}
