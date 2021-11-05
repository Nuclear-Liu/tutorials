package org.hui.design.patterns.factory_method.motivation;

/**
 * 文件分割器界面
 * 需求： 1.提供进度展示
 */
public class MainForm {
    public void button1Click() {
        // 面向接口编程 依赖于抽象，但是抽象依赖于细节
        Splitter splitter = new BinarySplitter();
        splitter.split();
    }
}
