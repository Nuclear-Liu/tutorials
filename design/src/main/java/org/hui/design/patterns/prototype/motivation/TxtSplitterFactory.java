package org.hui.design.patterns.prototype.motivation;

/**
 * 文本分割
 * 具体工厂
 */
public class TxtSplitterFactory implements SplitterFactory {
    @Override
    public Splitter CreateSplitter() {
        return new TxtSplitter();
    }
}
