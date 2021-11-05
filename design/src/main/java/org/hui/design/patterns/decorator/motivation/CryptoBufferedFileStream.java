package org.hui.design.patterns.decorator.motivation;

/**
 * 加密缓冲文件流.
 */
public class CryptoBufferedFileStream extends FileStream {
    @Override
    public char read(int number) {
        // 额外的加密操作...
        // 额外的缓冲操作...
        return super.read(number);
    }

    @Override
    public void seek(int position) {
        // 额外的加密操作...
        // 额外的缓冲操作...
        super.seek(position);
    }

    @Override
    public void write(char data) {
        // 额外的加密操作...
        // 额外的缓冲操作...
        super.write(data);
    }
}
