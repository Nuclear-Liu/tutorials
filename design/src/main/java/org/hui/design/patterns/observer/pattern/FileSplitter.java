package org.hui.design.patterns.observer.pattern;

import java.util.List;
import java.util.logging.Logger;

/**
 * 分割文件.
 */
public class FileSplitter {
    String mFilePath;
    int mFileNumber;
    List<Progress> progresses; // 抽象的通知机制 支持多个观察者

    public FileSplitter(String mFilePath, int mFileNumber) {
        this.mFilePath = mFilePath;
        this.mFileNumber = mFileNumber;
    }

    /**
     * 添加观察者.
     * @param progress
     */
    public void addProgress(Progress progress) {
        progresses.add(progress);
    }

    /**
     * 删除观察者.
     * @param progress
     */
    public boolean removeProgress(Progress progress) {
        return progresses.remove(progress);
    }

    public void split() {
        Logger.getGlobal().info("FileSplitter.split()");
        // 1.读取文件
        // 2.分批次向小文件中写入
        for (int i = 0; i < mFileNumber; i++) {
            // ...
            // 更新 MainForm 中 Progress Bar
            float progressValue = mFileNumber;
            progressValue = (i + 1) / progressValue;
            onProgress(progressValue);
        }
    }

    /**
     * protected 以供子类重写.
     * 发送通知.
     * @param value
     */
    protected void onProgress(float value) {
        if (progresses != null) {
            for (Progress progress : progresses) {
                progress.doProgress(value);  // 更新通知
            }
        }
    }
}
