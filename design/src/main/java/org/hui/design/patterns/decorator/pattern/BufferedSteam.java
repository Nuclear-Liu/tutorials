package org.hui.design.patterns.decorator.pattern;

/**
 * 扩展操作
 * 缓冲流
 */
public class BufferedSteam extends DecoratorStream {
    // private Stream stream; // 具体Stream 实现 公共字段应该提取到抽象中
    // ...

    public BufferedSteam(Stream stream) {
        super(stream);
    }

    @Override
    public char read(int number) {
        stream.read(number);
        return 0;
    }

    @Override
    public void seek(int position) {
        stream.seek(position);
    }

    @Override
    public void write(char data) {
        stream.write(data);
    }
}
