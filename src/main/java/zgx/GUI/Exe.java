package zgx.GUI;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


public class Exe extends Thread{
    Map<String,String> map = new HashMap();
    //进度条常量
    private static final int MIN_PROGRESS = 0;
    private static final int MAX_PROGRESS = 100;
    private  static int currentProgress = MIN_PROGRESS;
    //窗口
    static JFrame jf;
    static JFrame jf1 = new JFrame("编辑");
    //校验URL
    private String url = "https://c.qq.com/user/login?from=https%3A%2F%2Fc.qq.com%2Fcmdty%2Fdetail%3FcsId%3D17690";
    //账号 密码
    private String userNum = "";
    private String passwdNum = "";
    /*DateFormat.getDateInstance()为获取当前日期
     * DateFormat.getTimeInstance()为获取当前时间
     * DateFormat.getDateTimeInstance()为获取当前日期时间
     */
    Date date = new Date();
    DateFormat df = DateFormat.getDateTimeInstance();
    String nowDateTime = df.format(date);
    DefaultTableModel model;
    JTextArea textArea01 =new JTextArea(29, 20);
    JTextArea textArea= textArea = new JTextArea(25, 20);;



    //通过构造器  校验时多线程传参
    public Exe(String userNum, String passwdNum) {
        this.userNum = userNum;
        this.passwdNum = passwdNum;
    }

    public JFrame getJf1() {
        return jf1;
    }

    public Exe() {
        //获取JVM的系统属性
        Properties pps = System.getProperties();
        pps.list(System.out);

        //创建一个顶层容器（窗口）
        jf = new JFrame("中港星自动化程序");
        //设置窗口大小
        jf.setSize(300, 600);
        //把窗口位置设置到屏幕中心
        jf.setLocationRelativeTo(null);
        //当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //禁止缩放
        jf.setResizable(false);
    }

    public static void main(String[] args) {
        JPanel panel01 = new Exe().getPanel01();
        jf.setContentPane(panel01);
        //显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);
    }

    /**
     * 加载panel01 并返回
     * @return
     */
    @SuppressWarnings("all")
    private JPanel getPanel01() {
        //创建中间容器（面板容器）使用流式布局管理器
        final JPanel panel01 = new JPanel(new FlowLayout());
        // 创建 账号管理 按钮
        final JButton editBtn = new JButton("    账号管理    ");
        // 添加按钮的点击事件监听器
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel01.hide();
                //把 面板容器 作为窗口的内容面板 设置到 窗口
                jf.setContentPane(getPanel02());
            }
        });
        panel01.add(editBtn);
        // 创建 直接开始 按钮
        final JButton asUsualStartBtn = new JButton("无需管理账号，直接开始");
        // 添加按钮的点击事件监听器
        asUsualStartBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel01.hide();
                jf.setSize(500,800);
                //把 面板容器 作为窗口的内容面板 设置到 窗口
                jf.setContentPane(getPanel03());
            }
        });
        panel01.add(asUsualStartBtn);
        //说明
        JLabel infoLabel = new JLabel();
        infoLabel.setText("    ▼  账号列表  ▼    ");
        infoLabel.setFont(new Font(null, Font.PLAIN, 14));  // 设置字体，null 表示使用默认字体
        panel01.add(infoLabel);
        // 设置自动换行
        textArea01.setLineWrap(true);
        //背景颜色
        textArea01.setBackground(Color.LIGHT_GRAY);
        // 设置文本框是否可编辑
        textArea01.setEditable(false);
        //自动换行
        textArea01.setLineWrap(true);
        //获取账号列表
       setTextArea(textArea01);
        // 创建滚动面板, 指定滚动显示的视图组件(textArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane scrollPane01 = new JScrollPane(
                textArea01,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        panel01.add(scrollPane01);
        return panel01;
    }

    /**
     * 加载panel02 并返回
     * @return
     */
    private JPanel getPanel02() {
        //创建中间容器（面板容器）使用流式布局管理器
        final JPanel panel02 = new JPanel(new FlowLayout());
        //账号 字
        JLabel accountLabel = new JLabel();
        accountLabel.setText("账号");
        accountLabel.setFont(new Font(null, Font.PLAIN, 25));  // 设置字体，null 表示使用默认字体
        panel02.add(accountLabel);
        // 账号 输入框
        final JTextField userField = new JTextField(12);
        userField.setFont(new Font(null, Font.PLAIN, 20));
        panel02.add(userField);
        //密码 字
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("密码");
        passwordLabel.setFont(new Font(null, Font.PLAIN, 25));  // 设置字体，null 表示使用默认字体
        panel02.add(passwordLabel);
        // 密码 输入框
        final JTextField passwordField = new JTextField(12);
        passwordField.setFont(new Font(null, Font.PLAIN, 20));
        panel02.add(passwordField);
        // 显示域
        // 设置自动换行
        textArea.setLineWrap(true);
        //背景颜色
        textArea.setBackground(Color.WHITE);
        // 设置文本框是否可编辑
        textArea.setEditable(false);
        //自动换行
        textArea.setLineWrap(true);
        //获取账号列表
        setTextArea(textArea);

        //文本框监听
        final FocusListener focusListener=new FocusListener() {
            //获得鼠标焦点
            @Override
            public void focusGained(FocusEvent e) {
                JPanel jPanel = editTable();
                jf1.setContentPane(jPanel);
                //调整窗口的大小, 以适合其子组件的首选大小和布局。
                jf1.pack();
                //设置窗口的相对位置。
                jf1.setLocationRelativeTo(null);
                //设置窗口是否可见
                jf1.setVisible(true);
                textArea.setEnabled(false);
            }
            //失去鼠标焦点
            @Override
            public void focusLost(FocusEvent e) {
                textArea.setEnabled(true);
            }
        };
        textArea.addFocusListener(focusListener);
        // 创建滚动面板, 指定滚动显示的视图组件(textArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane scrollPane = new JScrollPane(
                textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        // 创建一个按钮，点击后校验账号
        final JButton checkBtn = new JButton("校验账号");
        checkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInput(userField, passwordField)) {
                    userNum = userField.getText().trim();
                    passwdNum = passwordField.getText().trim();
                    new Exe(userNum,passwdNum).start();
                }
            }
        });
        panel02.add(checkBtn);
        // 创建一个按钮，点击后进行输入值的基础校验，通过则将输入值保存文件并清空输入框
        JButton continueBtn = new JButton("添加账号");
        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkInput(userField, passwordField)) {
                    System.out.println("提交，账号为: " + userField.getText());
                    System.out.println("提交，密码为: " + passwordField.getText());
                    map.put(userField.getText(), passwordField.getText());
                    textArea.append(userField.getText() + "\r\n");
                    userField.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(null, "添加成功", "yeah！", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        });
        panel02.add(continueBtn);
        // 添加到内容面板(放在这里，是为了在前面两个按钮下面)
        panel02.add(scrollPane);
        // 创建一个按钮，点击后开始运行程序
        JButton startBtn = new JButton("开始程序");
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        jf,
                        "确认开始？",
                        "技术部温馨提示",
                        JOptionPane.YES_NO_OPTION
                );
                //根据YES_NO_OPTION（确认0，取消1）提示框
                switch (result) {
                    case 0:
                        panel02.hide();
                        jf.setSize(500,800);
                        JPanel panel03 = getPanel03();
                        jf.setContentPane(panel03);
                        break;
                    case 1:
                        break;
                }
            }
        });
        panel02.add(startBtn);
        return panel02;
    }

    /**
     * 加载panel03 并返回
     * @return
     */
    @SuppressWarnings("all")
    private JPanel getPanel03() {
        JPanel panel03 = new JPanel();

        // 创建一个进度条
        final JProgressBar progressBar = new JProgressBar();
        //设置长度，高度
        progressBar.setPreferredSize(new Dimension(420,15));
        // 设置进度的 最小值 和 最大值
        progressBar.setMinimum(MIN_PROGRESS);
        progressBar.setMaximum(MAX_PROGRESS);
        // 设置当前进度值
        progressBar.setValue(currentProgress);
        // 绘制百分比文本（进度条中间显示的百分数）
        progressBar.setStringPainted(true);
        // 添加进度改变通知
        progressBar.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("当前进度值: " + progressBar.getValue() + "; " +
                        "进度百分比: " + progressBar.getPercentComplete());
            }
        });
        // 添加到内容面板
        panel03.add(progressBar);
        jf.setContentPane(panel03);
        jf.setVisible(true);
        // 模拟延时操作进度, 每隔 0.5 秒更新进度
        new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentProgress++;
                if (currentProgress > MAX_PROGRESS) {
                    currentProgress = MIN_PROGRESS;
                }
                progressBar.setValue(currentProgress);
            }
        }).start();

        JLabel label04 = new JLabel();
        label04.setText("  账号总数:");
        label04.setFont(new Font(null, Font.PLAIN, 14));  // 设置字体，null 表示使用默认字体
        panel03.add(label04);
        final JTextField textField04 = new JTextField(3);
        //去除边框
        textField04.setBorder(new EmptyBorder(0,0,0,0));
        textField04.setEditable(false);
        textField04.setFont(new Font(null, Font.PLAIN, 14));
        panel03.add(textField04);
        JLabel label05 = new JLabel();
        label05.setText("当前序号:");
        label05.setFont(new Font(null, Font.PLAIN, 14));  // 设置字体，null 表示使用默认字体
        panel03.add(label05);
        final JTextField textField05 = new JTextField(3);
        //去除边框
        textField05.setBorder(new EmptyBorder(0,0,0,0));
        textField05.setEditable(false);
        textField05.setFont(new Font(null, Font.PLAIN, 11));
        panel03.add(textField05);
        JLabel label03 = new JLabel();
        label03.setText("当前账号:");
        label03.setFont(new Font(null, Font.PLAIN, 14));  // 设置字体，null 表示使用默认字体
        panel03.add(label03);
        final JTextField textField03 = new JTextField(16);
        //去除边框
        textField03.setBorder(new EmptyBorder(0,0,0,0));
        textField03.setEditable(false);
        textField03.setFont(new Font(null, Font.PLAIN, 11));
        panel03.add(textField03);

        // 日志窗口
        final JTextArea textArea02 = new JTextArea(43, 35);
        // 设置自动换行
        textArea02.setLineWrap(true);
        //背景颜色
        textArea02.setBackground(Color.LIGHT_GRAY);
        // 设置文本框是否可编辑
        textArea02.setEditable(false);
        //自动换行
        textArea02.setLineWrap(true);
        // 创建滚动面板, 指定滚动显示的视图组件(textArea), 垂直滚动条一直显示, 水平滚动条从不显示
        JScrollPane scrollPane02 = new JScrollPane(
                textArea02,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        panel03.add(scrollPane02);
        return panel03;
    }

    /**
     * 校验输入框输入的值
     * @param userField
     * @param passwordField
     * @return 值有问题返回false，没问题返回true
     */
    private boolean checkInput(JTextField userField, JTextField passwordField) {
        if(userField.getText().trim().length()==0||new String(passwordField.getText()).trim().length()==0) {
            JOptionPane.showMessageDialog(null, "账号或密码不允许为空", "提示", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(!userField.getText().matches("^[0-9]*$")) {
            JOptionPane.showMessageDialog(null, "账号有其它字符", "提示", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if(userField.getText().trim().length()<6){
            JOptionPane.showMessageDialog(null, "账号太短", "提示", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (passwordField.getText().trim().length()<6){
            JOptionPane.showMessageDialog(null, "密码太短", "提示", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * 登录腾讯，校验账号
     */
    @SuppressWarnings("all")
    @Override
    public void run() {
        WebDriver driver = null;
        try {
            String key = "webdriver.chrome.driver";
            String value = "src/resources/baseSoftware/chromedriver.exe";
            System.setProperty(key, value);
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--start-maximized");
            driver = new ChromeDriver(chromeOptions);
            driver.get(url);
            WebElement iframe = driver.findElement(By.id("login_frame"));
            WebDriver frame = driver.switchTo().frame(iframe);
            WebElement element = frame.findElement(By.xpath("//*[@id=\"switcher_plogin\"]"));
            element.click();
            WebElement user = frame.findElement(By.id("u"));
            user.sendKeys(userNum);
            WebElement pwd = frame.findElement(By.id("p"));
            pwd.sendKeys(passwdNum);
            WebElement login_button = frame.findElement(By.id("login_button"));
            login_button.click();
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            driver.quit();
        }
    }

    private JPanel editTable() {
        // 创建内容面板，使用边界布局
        JPanel panel = new JPanel(new BorderLayout());
        // 表头（列名）
        Object[] columnNames = {"账号", "密码"};
        LinkedHashMap<String, String> keyValueMap = ExeFile.getKeyValueMap();
        Set<Map.Entry<String, String>> entries = keyValueMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        int rows = entries.size();
        // 表格所有行数据
        String[][] rowData = new String[rows][2];
        int i = 0;
        while (iterator.hasNext()) {
            //开辟了一个长度为2的String数组
            String keyValue[] = new String[2];
            Map.Entry<String, String> entry = iterator.next();
            keyValue[0] = entry.getKey();
            keyValue[1] = entry.getValue();
            rowData[i] = keyValue;
            i++;
        }
        // 创建一个表格，指定 所有行数据 和 表头
        model = new DefaultTableModel(rowData, columnNames);
        final JTable table = new JTable(model);
        table.setRowHeight(30);
        TableColumn tableColumn1 = table.getColumnModel().getColumn(0);
        tableColumn1.setPreferredWidth(120);
        TableColumn tableColumn2 = table.getColumnModel().getColumn(1);
        tableColumn2.setPreferredWidth(200);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        // 把 表格内容 添加到容器中心
        panel.add(table, BorderLayout.CENTER);
        //确定按钮
        JButton okBtn = new JButton("确定");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JTable 失去焦点时取消编辑状态
                if (table.isEditing())
                    table.getCellEditor().stopCellEditing();
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "确认修改？",
                        "提示",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == 0) {
                    //更新修改结果，将表格全部数据取出
                    List<String> keyList = new ArrayList();
                    List<String> valueList = new ArrayList();
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        for (int row = 0; row < table.getRowCount(); row++) {
                            if (col==0) {
                                keyList.add((String) table.getValueAt(row, col));
                            }else {
                                valueList.add((String) table.getValueAt(row, col));
                            }
                        }
                    }
                    //用map封装 键值对
                    Map<String, String> updateMap = new HashMap<String, String>();
                    for (int j = 0; j < keyList.size(); j++) {
                        updateMap.put(keyList.get(j), valueList.get(j));
                    }
                    System.out.println("map封装" + updateMap.toString());
                    //将一个Map<String, String>写入properties文件,并且覆盖原来的内容
                    try {
                        ExeFile.writeProperties(updateMap);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    setTextArea(textArea);
                    jf1.dispose();
                }
            }
        });
        panel.add(okBtn, BorderLayout.PAGE_END);
        //删除按钮
        JButton deleteBtn = new JButton("删除");

        /*Object select = table.getValueAt(selectRol, selectCol);
        System.out.println(select);*/
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "确认删除？",
                        "提示",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == 0) {
                    //返回第一个选定行的索引；如果没有选定的行，则返回 -1。
                    int selectRol = table.getSelectedRow();
                    if (selectRol == -1) {
                        JOptionPane.showMessageDialog(null, "请选择要删除的行!");
                    }else {
                        model.removeRow(selectRol);
                    }
                }
            }
        });
        panel.add(deleteBtn, BorderLayout.PAGE_START);
       /* //删除按钮
        JButton deleteAllBtn = new JButton("全部删除");
        deleteAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                        null,
                        "确认全部删除？",
                        "提示",
                        JOptionPane.YES_NO_OPTION
                );
                if (result == 0) {
                    try {
                        ExeFile.clearProperties();
                    } catch (IOException ex) {
                        System.out.println(nowDateTime+"【IO】清空账号异常"+ex);
                    }
                    jf1.dispose();
                }
            }
        });
        panel.add(deleteAllBtn, BorderLayout.PAGE_START);*/
        return panel;
    }

    private void setTextArea(JTextArea textAreaDemo) {
        //清空JTextArea数据
        textAreaDemo.setText("");
        //获取账号列表
        Set<String> keys = ExeFile.getKeys();
        int i = 0;
        for (String key : keys) {
            textAreaDemo.append("【账号" + ++i + "】 " + "" + key + "" + "\r\n");
        }
    }
}

