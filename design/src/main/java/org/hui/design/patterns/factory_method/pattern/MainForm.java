package org.hui.design.patterns.factory_method.pattern;

/**
 * 文件分割器界面
 * 需求： 1.提供进度展示
 */
public class MainForm {
    SplitterFactory factory; // 放具体Factory 不可以在这里 构造函数传入
    public MainForm(SplitterFactory factory) {
        this.factory = factory;
    }
    public void button1Click() {
        // 面向接口编程 依赖于抽象，但是抽象依赖于细节
        Splitter splitter = factory.CreateSplitter(); // 多态 new
        splitter.split();
    }
}
