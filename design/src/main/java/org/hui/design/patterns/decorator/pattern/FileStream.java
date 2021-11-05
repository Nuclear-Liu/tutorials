package org.hui.design.patterns.decorator.pattern;

/**
 * 文件流
 */
public class FileStream implements Stream {
    @Override
    public char read(int number) {
        // 读文件
        return 0;
    }

    @Override
    public void seek(int position) {
        // 定位文件流
    }

    @Override
    public void write(char data) {
        // 写文件流
    }
}
