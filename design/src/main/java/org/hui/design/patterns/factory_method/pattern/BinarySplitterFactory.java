package org.hui.design.patterns.factory_method.pattern;

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
