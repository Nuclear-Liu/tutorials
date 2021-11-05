package org.hui.design.patterns.prototype.pattern;

/**
 * 图片分割
 */
public class PictureSplitter implements Splitter {
    @Override
    public void split() {
        // split...
    }

    @Override
    public Splitter factoryClone() {
        PictureSplitter pictureSplitter = new PictureSplitter();
        // 设置属性
        return pictureSplitter;
    }
}
