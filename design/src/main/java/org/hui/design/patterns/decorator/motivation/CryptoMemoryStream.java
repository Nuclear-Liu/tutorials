package org.hui.design.patterns.decorator.motivation;

/**
 * 扩展操作
 * 加密内存流
 */
public class CryptoMemoryStream extends MemoryStream {
    @Override
    public char read(int number) {
        // 额外的加密操作...
        return super.read(number); // 读文件流
        // 额外的加密操作...
    }

    @Override
    public void seek(int position) {
        // 额外的加密工作...
        super.seek(position); // 定位文件流
        // 额外的加密工作...
    }

    @Override
    public void write(char data) {
        // 额外的加密工作...
        super.write(data); // 写文件流
        // 额外的加密工作...
    }
}
