package org.hui.design.patterns.prototype.motivation;

/**
 * 分割文件.
 * 具体工厂
 */
public class BinarySplitterFactory implements SplitterFactory {
    @Override
    public Splitter CreateSplitter() {
        return new BinarySplitter();
    }
}
