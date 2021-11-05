package org.hui.design.patterns.prototype.pattern;

/**
 * 文件分割器界面
 * 需求： 1.提供进度展示
 */
public class MainForm {
    Splitter prototype; // 原型对象
    public MainForm(Splitter prototype) {
        this.prototype = prototype;
    }
    public void button1Click() {
        // 面向接口编程 依赖于抽象，但是抽象依赖于细节
        Splitter splitter = prototype.factoryClone(); // 克隆原型得到新对象
        splitter.split();
    }
}
