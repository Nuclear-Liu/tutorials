package org.hui.design.patterns.observer.pattern;


import java.util.logging.Logger;

/**
 * 文件分割器界面
 * 需求： 1.提供进度展示
 *       2.支持多个观察者（MainForm）
 */
public class MainForm implements Progress {
    //    TextBox txtFilePath; // 输入文件路径
//    TextBox txtFileNumber; // 要分割文件的数量
    Progress progressBar; // 进度条
    Progress consoleNotifier; // 控制台进度
    public void button1Click() {
//        String filePath = txtFilePath.getText();
//        int number = atoi(txtFileNumber.getText().c_str());
        String filePath = "";
        int number = 0;
        FileSplitter splitter = new FileSplitter(filePath, number);
        splitter.addProgress(progressBar); // 订阅通知
        splitter.addProgress(consoleNotifier); // 订阅通知
        splitter.split();
    }
    @Override
    public void doProgress(float value) {
        Logger.getGlobal().info("Progress Info.");
        // 设置进度信息
    }
}
