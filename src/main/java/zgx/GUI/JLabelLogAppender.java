package zgx.GUI;

import java.io.IOException;
import java.util.Scanner;
import javax.swing.JLabel;
/**
 * 不间断地扫描输入流
 * 将扫描到的字符流显示在JLabel上
 * JLabel在java swing里面,主要用来显示文字的,比如不可单击,只用来展示的. label标签
 */
public class JLabelLogAppender extends AbstractLogAppender{
    private JLabel label;
    /**
     * 默认的构造
     * @param label 记录器名称，该记录器输出的日志信息将被截取并输出到指定的JLabel组件
     * @throws IOException
     */
    public JLabelLogAppender(JLabel label) throws IOException {
        //super()从子类中调用父类的构造方法(构造函数中的第一条语句)
        super();
        //this表示一个对象的引用,它指向正在执行方法的对象
        this.label = label;
    }
    /**
     * 父类继承了Thread类
     */
    @Override
    public void run() {
        // 不间断地扫描输入流
        // Scanner reader=new Scanner(System.in) 新建一个参数为System.in的Scanner对象 System.in参数是键盘输入内容，对象（reader）有许多的方法如：reader.next（）；指的是键盘输入的文字内容，在用String a来接收就是：String a=input.next()
        Scanner scanner = new Scanner(reader);
        // 将扫描到的字符流显示在指定的JLabel上
        while (scanner.hasNextLine()) {
            try {
                Thread.sleep(100);
                String line = scanner.nextLine();
                label.setText(line);
                line = null;
            } catch (Exception ex) {
                //异常信息不作处理
            }
        }
    }
}
