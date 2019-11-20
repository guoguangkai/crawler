package zgx.Auto;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TxtUtil {
    public static void readAndWriteTxt(String txtFilePath) throws IOException {
        // 使用ArrayList来存储每行读取到的字符串
        Map<String, String> map = new HashMap<String, String>();
        /* 读取数据 */
        BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(txtFilePath)), "UTF-8"));
        String lineTxt = null;
        // 按行读取字符串
        int i = 1;
        while ((lineTxt = bf.readLine()) != null) {
            map.put(String.valueOf(i),lineTxt);
            i++;
        }
        System.out.println(map.toString());
        bf.close();

        /* 输出数据 */
        Properties properties=new Properties();
        OutputStream fos = new FileOutputStream("src/resources/url.properties");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
        for (String key : map.keySet()) {
            properties.setProperty(key, map.get(key));
        }
        for(Enumeration<?> e = properties.keys(); e.hasMoreElements();) {
            String key = (String)e.nextElement();
            String val = properties.getProperty(key);
            bw.write(key + "=" + val);
            bw.newLine();
        }
        bw.flush();
    }

}
