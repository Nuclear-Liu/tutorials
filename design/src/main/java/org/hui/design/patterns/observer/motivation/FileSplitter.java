package org.hui.design.patterns.observer.motivation;

import java.util.logging.Logger;

/**
 * 分割文件.
 */
public class FileSplitter {
    String mFilePath;
    int mFileNumber;
//    ProgressBar mProgressBar; // 引用 MainForm 中的 Progress Bar  -> 通知
    public FileSplitter(String mFilePath, int mFileNumber/*, ProgressBar mProgressBar*/) {
        this.mFilePath = mFilePath;
        this.mFileNumber = mFileNumber;
//        this.mProgressBar = mProgressBar;
    }
    public void split() {
        Logger.getGlobal().info("FileSplitter.split()");
        // 1.读取文件
        // 2.分批次向小文件中写入
        for (int i = 0; i < mFileNumber; i++) {
            // ...
            /* 更新 MainForm 中 Progress Bar
            if (mProgressBar != null) {
            mProgressBar.setValue((i + 1) / mFileNumber); // 更新进度
            }
             */
        }
    }
}
