package org.hui.design.patterns.factory_method.pattern;

/**
 * 视频分割
 * 具体工厂
 */
public class VideoSplitterFactory implements SplitterFactory {
    @Override
    public Splitter CreateSplitter() {
        return new VideoSplitter();
    }
}
