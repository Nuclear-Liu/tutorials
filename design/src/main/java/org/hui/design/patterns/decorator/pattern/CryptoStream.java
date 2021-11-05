package org.hui.design.patterns.decorator.pattern;

/**
 * 扩展操作
 * 加密文件流
 */
public class CryptoStream extends DecoratorStream {
    // private Stream stream; // 组合替代继承 = new FileStream 公共字段应该提取到抽象中
    // 其他 Stream 实现

    public CryptoStream(Stream stream) {
        super(stream);
    }

    @Override
    public char read(int number) {
        // 额外的加密操作...
        return stream.read(number); // 读文件流
        // 额外的加密操作...
    }

    @Override
    public void seek(int position) {
        // 额外的加密工作...
        stream.seek(position); // 定位文件流
        // 额外的加密工作...
    }

    @Override
    public void write(char data) {
        // 额外的加密工作...
        stream.write(data); // 写文件流
        // 额外的加密工作...
    }
}
