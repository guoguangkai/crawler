package http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URISyntaxException;

public class WithParameterHttpGet {
    public static void main(String[] args) throws URISyntaxException {
        //打开浏览器  创建HttpClient对象
        CloseableHttpClient httpClient= HttpClients.createDefault();
        //请求地址是：http://yun.itheima.com/search?keys=Java
        //创建URIBuilder
        URIBuilder uriBuilder = new URIBuilder("http://yun.itheima.com/search");
        uriBuilder.setParameter("key", "java");//如果有多个参数，根据返回的依旧是uribuilder，继续set下去
        //输入网址  发起get请求，创建HttpGet对象
        HttpGet httpGet = new HttpGet(uriBuilder.build());
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
