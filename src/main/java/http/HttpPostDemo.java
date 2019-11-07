package http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpPostDemo {
    public static void main(String[] args) {
        //打开浏览器  创建HttpClient对象
        CloseableHttpClient httpClient=HttpClients.createDefault();
        //输入网址  发起Post请求，创建HttpGet对象
        HttpPost httpPost = new HttpPost("http://www.itcast.cn");
        CloseableHttpResponse response = null;
        try {
            //按回车，发起请求，返回响应
            response = httpClient.execute(httpPost);
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
