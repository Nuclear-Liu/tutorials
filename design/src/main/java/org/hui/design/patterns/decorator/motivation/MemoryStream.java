package org.hui.design.patterns.decorator.motivation;

/**
 * 内存流
 */
public class MemoryStream implements Stream {
    @Override
    public char read(int number) {
        // 读内存流
        return 0;
    }

    @Override
    public void seek(int position) {
        // 定位内存流
    }

    @Override
    public void write(char data) {
        // 写内存流
    }
}
