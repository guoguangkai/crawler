package http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
//https://blog.csdn.net/weixin_34319999/article/details/88755671
public class ClientPool {
    public static void main(String[] args) {
        //创建连接池管理器
        PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager();
        //设置总共最大连接数
        clientConnectionManager.setMaxTotal(100);
        //设置每个主机的最大连接数（爬虫时可能会访问到不同的主机，均衡分配）
        clientConnectionManager.setDefaultMaxPerRoute(10);
        //使用连接池管理器发起请求
        doGet(clientConnectionManager);
    }

    private static void doGet(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
        //不是每次创建新的HttpClient,而是从连接池中获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();
        HttpGet httpGet = new HttpGet("http://www.itcast.cn");
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
            //现在由连接池管理器关闭
           /* try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
    }
}
