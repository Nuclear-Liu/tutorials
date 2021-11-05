package org.hui.design.patterns.decorator.pattern;

/**
 * 具体使用
 */
public class Process {
    // 运行时装配
    FileStream fileStream = new FileStream();
    CryptoStream cryptoStream = new CryptoStream(fileStream);
    BufferedSteam bufferedSteam = new BufferedSteam(fileStream);
    BufferedSteam bufferedSteam2 = new BufferedSteam(cryptoStream);
}
