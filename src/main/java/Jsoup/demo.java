package Jsoup;

import javax.swing.*;

//解释一波 这是创建窗口到添加标签"hello world"然后显示的实例
public class demo{
   // 创建并显示GUI，出于线程安全的考虑
    //这个方法在事件调用线程中调用

    private static void createAndShowGUI(){
        //创建一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        //创建及设置窗口
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //添加“Hello World 标签”
        JLabel label = new JLabel("hello world");
        frame.getContentPane().add(label);

        //显示窗口
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        //显示应用GUI（这调用方法吊炸天）
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}