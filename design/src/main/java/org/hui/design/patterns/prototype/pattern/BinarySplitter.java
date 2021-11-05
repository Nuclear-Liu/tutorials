package org.hui.design.patterns.prototype.pattern;

/**
 * 分割文件.
 * 一个对象的类型最好声明成接口
 */
public class BinarySplitter implements Splitter {
    @Override
    public void split() {
        // split...
    }
    @Override
    public Splitter factoryClone() {
        BinarySplitter binarySplitter = new BinarySplitter();
        // 设置相关属性
        return binarySplitter;
    }
}
