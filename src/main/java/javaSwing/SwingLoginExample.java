package javaSwing;
//再解释一波，这跟上面那啥HelloWorldSwing没好大区别，就是多了几个方法的运用
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;					//这些import你别管它是啥，用就完事了
public class SwingLoginExample {
//https://www.cnblogs.com/davidwang456/articles/4585456.html
    //https://www.jb51.net/article/154334.htm
  /*  JFrame

    java的GUI程序的基本思路是以JFrame为基础，说白了就是窗体屏幕它是屏幕上window的对象，能够最大化，最小化，关闭。
    JPanel

    java图形用户界面（GUI）工具包swing中的面板容器类，包含在javax.swing包中，可以进行嵌套，功能是对窗体中具有相同逻辑功能的组件进行组合，是一种轻量级容器，可以加入到JFrame窗体中。
    呃，好吧实力有限以后补解释。
    JLabel

    JLabel对象可以显示文本、图像或同时显示二者。可以通过设置垂直和水平对齐方式，指定标签显示区中标签内容在何处对齐。默认情况下，标签在显示区内垂直居中对齐。默认情况下，只显示文本的标签是开始边对齐。而只显示图像的标签则水平居中对齐。好累 这些官方话敲的我好累。。。偏偏我又不会用白话解释
            JTextField

    一个轻量级组建，允许编辑单行文本。
    JPasswordField

    允许我们输入了一行字像输入框，但隐藏星号（*）或点创建密码（密码） 学习过javascript的同学应该明白 类似的注册表单嘛
    JButton

    JButton类的实例。用于创建按钮类似实例中的“Login”。*/

    public static void main(String[] args) {

        //好的，日常用JFrame创建一个窗口对象frame，然后设置关闭建
        JFrame frame = new JFrame("Login Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //多了一个对frame对象窗口的操作，嗯 设计下窗口大小还不错
        frame.setSize(350,200);

        //创建面板JPanel，这个类似于HTML的div标签
        //我们可以创建多个面板并在JFrame中指定位置，面板中我们可以添加文本字段，按钮及其他组件

        //哎哟，说了一大堆，其实就是可以在窗口里面添加东西啦
        //当然前提是用JPanel创建了一个面板对象panel，因为我们要添加只能通过这个面板（当然还有其他方法哈，不止这一个）
        JPanel panel = new JPanel();

        //日常添加到窗口frame中
        frame.add(panel);

        //调用用户定义的方法并添加组件到面板
        placeComponents(panel);

        //好的，日常设置界面可见
        frame.setVisible(true);
    }

    //基本框架完成，现在往里面添加东西吧
    private static void placeComponents(JPanel panel) {

        //布局部分我们这边不多做介绍
        //这边设置布局为null
        panel.setLayout(null);

        //用JLabel创建一个userLabel标签对象，哎呀名字随便取，作用就是直接在屏幕上显示信息的
        //用法与一般的生成对象有点区别，据我估计是构造方法带参了...总之不影响我们学习
        JLabel userLabel = new JLabel("User:");

        //这个方法定义组件的位置 setBounds(x,y,width,height)，好，记住了，后面用的多
        userLabel.setBounds(10, 20, 80, 25);

        //日常添加到面板panel中
        panel.add(userLabel);

        //创建文本域(JTextField函数) 嗯，学到了 新函数 用来创建文本域输入信息的
        JTextField userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);

        //日常添加到面板panel中
        panel.add(userText);


        //创建JLabel 与上面操作一样的
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);

        //日常日常
        panel.add(passwordLabel);

        //换个文本域创建方法 用于保护密码安全 类似上面的JTextField方法
        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);

        //......
        panel.add(passwordText);


        //创建登陆按钮(JButton)
        JButton loginButton = new JButton("登陆");
        loginButton.setBounds(0, 100, 80, 25);

        //......
        panel.add(loginButton);
    }
}
