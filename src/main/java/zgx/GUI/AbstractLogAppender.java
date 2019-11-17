package zgx.GUI;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.Writer;
import org.apache.log4j.Logger;
import org.apache.log4j.Appender;
import org.apache.log4j.WriterAppender;

/**
 * 类描述：
 * 重置log4j的Appender的Writer
 * Log4j的输出终端（Appender接口）,Appender控制日志怎样输出
 * 这个类是一个基类,不能够直接使用,由它的子类负责将来自控制台的日志信息输出到UI组件。
 */
public abstract class AbstractLogAppender extends Thread {
    /*PipedReader和PipedWriter即管道输入流和输出流，可用于线程间管道通信。它们和PipedInputStream/PipedOutputStream区别是前者操作的是字符后者是字节*/
    protected PipedReader reader;
    //默认构造器
    public AbstractLogAppender() throws IOException {
        /*有很多方法可以创建一个日志记录器（Logger），下面方法可以取回root日志记录器:Logger logger = Logger.getRootLogger();
          还可以这样创建一个新的日志记录器:Logger logger = Logger.getLogger("MyLogger");
          比较常用的用法，就是根据类名实例化一个静态的全局日志记录器:static Logger logger = Logger.getLogger(test.class);*/
        Logger root = Logger.getRootLogger();
        root.debug("555555555555555555");
        // 获取子记录器的输出源
        Appender appender = root.getAppender("WriterAppender");
        // 定义一个未连接的输入流管道
        reader = new PipedReader();
        // 定义一个已连接的输出流管理，并连接到reader
        Writer writer = new PipedWriter(reader);
        // 设置 appender 输出流
        ((WriterAppender) appender).setWriter(writer);
    }

}