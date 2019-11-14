package test;

import java.io.IOException;
import java.util.List;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

/**
 * 模拟点击，动态获取页面信息
 * @author linhongcun
 *
 */
public class demo {
    public static void main(String[] args) throws Exception {
        // 创建webclient
        WebClient webClient = new WebClient();
        // 取消 JS 支持
        webClient.getOptions().setJavaScriptEnabled(false);
        // 取消 CSS 支持
        webClient.getOptions().setCssEnabled(false);
        // 获取指定网页实体
        HtmlPage page = (HtmlPage) webClient.getPage("https://www.so.com/");
        // 获取搜索输入框
        HtmlInput input = (HtmlInput) page.getHtmlElementById("input");
        // 往输入框 “填值”
        input.setValueAttribute("larger5");
        // 获取搜索按钮
        HtmlInput btn = (HtmlInput) page.getHtmlElementById("search-button");
        // “点击” 搜索
        HtmlPage page2 = btn.click();
        // 选择元素
        List<HtmlElement> spanList=page2.getByXPath("//h3[@class='res-title']/a");
        for(int i=0;i<spanList.size();i++) {
            // 输出新页面的文本
            System.out.println(i+1+"、"+spanList.get(i).asText());
        }
    }

    public void test() throws IOException {
        final WebClient webClient=new WebClient();
        final HtmlPage page=webClient.getPage("http://www.baidu.com");
        System.out.println(page.asText());  //asText()是以文本格式显示
        System.out.println(page.asXml());   //asXml()是以xml格式显示
        webClient.close();
    }

    public void test1() throws IOException {
        // 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了
        WebClient webclient = new WebClient();

        // 这里是配置一下不加载css和javaScript，因为httpunit对javascript兼容性不太好
        webclient.getOptions().setCssEnabled(false);
        webclient.getOptions().setJavaScriptEnabled(false);

        // 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可
        HtmlPage htmlpage = webclient.getPage("http://baidu.com");

        // 根据名字得到一个表单，查看上面这个网页的源代码可以发现表单的名字叫“f”
        final HtmlForm form = htmlpage.getFormByName("f");
        // 同样道理，获取”百度一下“这个按钮
        final HtmlSubmitInput button = form.getInputByValue("百度一下");
        // 得到搜索框
        final HtmlTextInput textField = form.getInputByName("q1");
        //搜索我的id
        textField.setValueAttribute("th是个小屁孩");
        // 输入好了，我们点一下这个按钮
        final HtmlPage nextPage = button.click();
        // 我把结果转成String
        String result = nextPage.asXml();

        System.out.println(result);  //得到的是点击后的网页
    }

}
