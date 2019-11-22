package zgx.Auto;

import java.io.*;
import java.util.*;

public class PropertiesUtil {
    static BufferedReader in;

    /**
     * 构造函数私有化
     */
    private PropertiesUtil() {
    }

    /**
     * properties的初始化操作
     */
    private static Properties initProperties(String filepath) throws IOException {
        //在往文件写入时Properties prop 不能多次new , 不然每次写入都会清掉 properties文件
        Properties prop = new Properties();
       /* FileReader类读取数据实质是InputStreamReader类在读取，而InputStreamReader读取数据实际是StreamDecoder类读取 因此在使用字符输入流的时候实际是StreamDecoder类在发挥作用*/
        //FileInputStream流被称为文件字节输入流，意思指对文件数据以字节的形式进行读取操作如读取图片视频等【以字节为单位，操作中文会乱码】
        //一个英文字母占一个字节，一个中文汉字占两个字节，而一个英文字母与一个中文汉字我们都称之为一个字符【FileReader】
        //or  InputStream in = getClass().getResourceAsStream("资源Name");
        in = new BufferedReader(new FileReader(filepath));
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
    public static String getValueByKey(String filepath,String key) {
        String value = null;
        try {
            Properties properties = initProperties(filepath);
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
    public static Set<String> getKeys(String filepath) {
        Set<String> keys = null;
        try {
            Properties properties = initProperties(filepath);
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
    public static LinkedHashMap<String, String> getKeyValueMap(String filepath) {
        Set<String> keys = null;
        LinkedHashMap<String,String> map = new LinkedHashMap<String, String>();
        try {
            Properties properties = initProperties(filepath);
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

    //追加写入Properties信息
    public static void appendProperties(String filepath,String pKey, String pValue) throws IOException {
        Properties properties =initProperties(filepath);
        //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
        //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
        FileWriter out = new FileWriter(filepath);
        properties.setProperty(pKey, pValue);
        //以适合使用 load 方法加载到 Properties 表中的格式，
        //将此 Properties 表中的属性列表（键和元素对）写入输出流
       /* #更新 pKey 账号
        #Mon Nov 18 21:52:57 CST 2019*/
        properties.store(out, "更新 " + pKey + " 账号");
        closeIO();
    }

    //清空Properties信息
    public static void clearProperties(String filepath) throws IOException {
        Properties properties = initProperties(filepath);
        properties.clear();
        FileWriter out = new FileWriter(filepath);
        properties.store(out,"清空");
        out.close();
        closeIO();
    }

    /**
     * 将一个Map<String, String>写入properties文件,并且覆盖原来的内容
     * @param map
     * @return
     */
    public static void writeProperties(String filepath,Map<String, String> map) throws IOException {
        Properties properties=new Properties();
        for (String key : map.keySet()) {
            properties.setProperty(key, map.get(key));
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filepath)), "UTF-8"));
        properties.store(bw,properties.toString());
        bw.close();

    }



    public static void main(String[] args) throws IOException {
    }
}
