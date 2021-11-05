package org.hui.design.patterns.factory_method.pattern;

/**
 * 图片分割
 * 具体工厂
 */
public class PictureSplitterFactory implements SplitterFactory {
    @Override
    public Splitter CreateSplitter() {
        return new BinarySplitter();
    }
}
