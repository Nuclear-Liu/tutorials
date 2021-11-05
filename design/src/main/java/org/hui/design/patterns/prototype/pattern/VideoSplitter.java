package org.hui.design.patterns.prototype.pattern;

/**
 * 视频分割
 */
public class VideoSplitter implements Splitter {
    @Override
    public void split() {
        // split...
    }
    @Override
    public Splitter factoryClone() {
        VideoSplitter videoSplitter = new VideoSplitter();
        // 设置属性
        return videoSplitter;
    }
}
