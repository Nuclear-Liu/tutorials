package org.hui.design.patterns.prototype.pattern;

public interface Splitter {
    void split();
    Splitter factoryClone(); // 通过克隆自己来创建对象
}
