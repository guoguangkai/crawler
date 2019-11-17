package zgx.GUI;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ExeFile {
    private static String filepath = "src/resources/account.properties";
    static InputStream in;

    /**
     * 构造函数私有化
     */
    private ExeFile() {
    }

    /**
     * properties的初始化操作
     */
    private static Properties initProperties() throws IOException {
        //在往文件写入时Properties prop 不能多次new , 不然每次写入都会清掉 properties文件
        Properties prop = new Properties();
        //FileInputStream流被称为文件字节输入流，意思指对文件数据以字节的形式进行读取操作如读取图片视频等【以字节为单位，操作中文会乱码】
        //一个英文字母占一个字节，一个中文汉字占两个字节，而一个英文字母与一个中文汉字我们都称之为一个字符【FileReader】
        in = new BufferedInputStream(new FileInputStream(filepath));
        //load(InputStream inStream)，从输入流中读取属性列表（键和元素对）。通过对指定的文件进行装载来获取该文件中的所有键-值对。
        prop.load(in);
        return prop;
    }

    /**
     * 关流
     */
    private static void closeIO() {
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据properties文件中的键获取值
     *
     * @param key
     * @return
     */
    public static String getValueByKey(String key) {
        String value = null;
        try {
            Properties properties = initProperties();
            //用指定的键在此属性列表中搜索属性。也就是通过参数key，得到key所对应的value。
            value = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeIO();
        }
        return value;
    }

    /**
     * 获取所有键
     *
     * @return
     */
    public static Set<String> getKeys() {
        Set<String> keys = null;
        try {
            Properties properties = initProperties();
            keys = properties.stringPropertyNames();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO();
        }
        return keys;
    }

    /**
     * 获取所有键值对
     * @return
     */
    public static LinkedHashMap<String, String> getKeyValueMap() {
        Set<String> keys = null;
        LinkedHashMap<String,String> map = new LinkedHashMap<String, String>();
        try {
            Properties properties = initProperties();
            keys = properties.stringPropertyNames();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key=iterator.next();
                String value = properties.getProperty(key);
                map.put(key, value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            closeIO();
        }
        return map;
    }

    public static void main(String[] args) {
        System.out.println(getKeyValueMap().toString());
    }
}
