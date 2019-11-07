package http;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpGetDemo {
    public static void main(String[] args) {
        //打开浏览器  创建HttpClient对象
        CloseableHttpClient httpClient=HttpClients.createDefault();
        //输入网址  发起get请求，创建HttpGet对象
        HttpGet httpGet = new org.apache.http.client.methods.HttpGet("http://www.itcast.cn");
        //配置请求信息
        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000) //创建最长连接的时间，单位毫秒
                .setConnectionRequestTimeout(500) //设置获取连接的最长时间
                .setSocketTimeout(10 * 1000) //设置数据传输的最长时间
                .build();
        //给请求设置请求信息
        httpGet.setConfig(config);
        CloseableHttpResponse response = null;
        try {
            //按回车，发起请求，返回响应
            response = httpClient.execute(httpGet);
            //解析响应，获取数据
            //判断状态码是否是200
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf8");
                System.out.println(content.length());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭资源
        finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
