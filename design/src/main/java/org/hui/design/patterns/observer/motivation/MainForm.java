package org.hui.design.patterns.observer.motivation;

/**
 * 文件分割器界面
 * 需求： 1.提供进度展示
 */
public class MainForm {
//    TextBox txtFilePath; // 输入文件路径
//    TextBox txtFileNumber; // 要分割文件的数量
//    ProgressBar progressBar; // 进度条
    public void button1Click() {
//        String filePath = txtFilePath.getText();
//        int number = atoi(txtFileNumber.getText().c_str());
        String filePath = "";
        int number = 0;
        FileSplitter splitter = new FileSplitter(filePath, number/*, ProgressBar progressBar*/);
        splitter.split();
    }
}
