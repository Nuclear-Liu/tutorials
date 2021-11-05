package org.hui.design.patterns.decorator.motivation;

/**
 * 网路流
 */
public class NetworkStream implements Stream {
    @Override
    public char read(int number) {
        // 读网络流
        return 0;
    }

    @Override
    public void seek(int position) {
        // 定位网络流
    }

    @Override
    public void write(char data) {
        // 写网络流
    }
}
