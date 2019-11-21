package zgx.Auto;

import java.io.*;

/**
 * 它用一个ByteArrayOutputStream提供和Java管道流类似的功能，但不会出现死锁和IOException异常。这个类的内部仍旧使用管道流
 */
public class LoopedStreams {
    private PipedOutputStream pipedOS = new PipedOutputStream();
    private boolean keepRunning = true;
    //构造函数 连接管道流，然后调用startByteArrayReaderThread()方法
    public LoopedStreams() throws IOException {
        pipedOS.connect(pipedIS);
        startByteArrayReaderThread();
    } // LoopedStreams()
    /*
    ByteArrayOutputStream具有根据需要扩展其内部缓冲区的能力。
    由于存在“完全缓冲”，线程向getOutputStream()返回的流写入数据时不会被阻塞。
    因而，第一个问题不会再给我们带来麻烦。另外还要顺便说一句，ByteArrayOutputStream的缓冲区永远不会缩减。
    例如，假设在能够提取数据之前，有一块500 K的数据被写入到流，缓冲区将永远保持至少500 K的容量。
    如果这个类有一个方法能够在数据被提取之后修正缓冲区的大小，它就会更完善。
     */
    private ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream() {
    public void close() {
        keepRunning = false;
        try {
            super.close();
            pipedOS.close();
        }
        catch(IOException e) {
            // 记录错误或其他处理
            // 为简单计，此处我们直接结束
            System.exit(1);
        }
    }
    };
    private PipedInputStream pipedIS = new PipedInputStream() {
        public void close() {
            keepRunning = false;
            try    {
                super.close();
            }
            catch(IOException e) {
                // 记录错误或其他处理
                // 为简单计，此处我们直接结束
                System.exit(1);
            }
        }
    };
    public InputStream getInputStream() {
        return pipedIS;
    }

    /*
    getOutputStream()方法返回一个OutputStream（具体地说，是一个ByteArrayOutputStream）用以替代PipedOutputStream。
    写入该OutputStream的数据最终将在getInputStream()方法返回的流中作为输入出现。
    和使用PipedOutputStream的情形不同，向ByteArrayOutputStream写入数据的线程的激活、写数据、结束不会带来负面效果。
     */
    public OutputStream getOutputStream() {
        return byteArrayOS;
    }
    /*
    startByteArrayReaderThread()方法是整个类真正的关键所在。这个方法的目标很简单，就是创建一个定期地检查ByteArrayOutputStream缓冲区的线程。
    缓冲区中找到的所有数据都被提取到一个byte数组，然后写入到PipedOutputStream。由于PipedOutputStream对应的PipedInputStream由getInputStream()返回，
    从该输入流读取数据的线程都将读取到原先发送给ByteArrayOutputStream的数据。
    实际上任何时候只有一个线程向PipedOutputStream写入数据，这个线程就是由startByteArrayReaderThread()创建的线程。
    由于这个线程完全由LoopedStreams类控制，我们不必担心它会产生IOException异常。
     */
    private void startByteArrayReaderThread() {
        new Thread(new Runnable() {
            public void run() {
                while(keepRunning) {
                    // 检查流里面的字节数
                    if(byteArrayOS.size() > 0) {
                        byte[] buffer = null;
                        synchronized(byteArrayOS) {
                            buffer = byteArrayOS.toByteArray();
                            byteArrayOS.reset(); // 清除缓冲区
                        }
                        try {
                            // 把提取到的数据发送给PipedOutputStream
                            pipedOS.write(buffer, 0, buffer.length);
                        }
                        catch(IOException e) {
                            // 记录错误或其他处理
                            // 为简单计，此处我们直接结束
                            System.exit(1);
                        }
                    }
                    else // 没有数据可用，线程进入睡眠状态
                        try {
                            // 每隔1秒查看ByteArrayOutputStream检查新数据
                            Thread.sleep(1000);
                        }
                        catch(InterruptedException e) {}
                }
            }
        }).start();
        } // startByteArrayReaderThread()
} // LoopedStreams