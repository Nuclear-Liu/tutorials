package org.hui.design.patterns.prototype.pattern;

/**
 * 文本分割
 */
public class TxtSplitter implements Splitter {
    @Override
    public void split() {
        // split...
    }

    @Override
    public Splitter factoryClone() {
        TxtSplitter txtSplitter = new TxtSplitter();
        // 设置属性
        return txtSplitter;
    }
}
