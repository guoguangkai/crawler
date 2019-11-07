package http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 参数放在表单中进行请求
 */
public class WithParameterHttpPost {
    public static void main(String[] args) throws Exception {
        //打开浏览器  创建HttpClient对象
        CloseableHttpClient httpClient= HttpClients.createDefault();
        //创建HttpPost对象，设置URL访问地址
        HttpPost httpPost = new HttpPost("http://yun.itheima.com/search");
        //声明List集合，封装表单中的参数
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        paramsList.add(new BasicNameValuePair("key", "java"));
        //创建表单的Entity对象
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(paramsList, "UTF8");
        //设置表单的Entity对象到post请求中
        httpPost.setEntity(formEntity);
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
