package org.hui.design.patterns.decorator.motivation;

/**
 * 具体使用
 */
public class Process {
    // 编译时装配
    CryptoFileStream cryptoFileStream = new CryptoFileStream();
    BufferedFileSteam bufferedFileSteam = new BufferedFileSteam();
    CryptoBufferedFileStream cryptoBufferedFileStream = new CryptoBufferedFileStream();
}
