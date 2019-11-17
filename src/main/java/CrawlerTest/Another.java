package CrawlerTest;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;

public class Another {
    public static void main(String[] args) throws IOException {
        WebClient webclient = new WebClient();
        System.out.println("初始化浏览器");
        webclient.getOptions().setCssEnabled(false);
        System.out.println("禁用css，避免自动二次请求进行渲染");
        webclient.getOptions().setJavaScriptEnabled(true);
        System.out.println("忽略ssl认证");
        webclient.setAjaxController(new NicelyResynchronizingAjaxController());
        System.out.println("支持ajax");
        webclient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webclient.getOptions().setThrowExceptionOnScriptError(false);
        HtmlPage page = webclient.getPage("https://c.qq.com/cmdty/detail?csId=17690");
        System.out.println("获取登录网页");
        DomNodeList<DomElement> alist = page.getElementsByTagName("a");
        String str = "HtmlAnchor[<a _stat_click_id=\"7_320425\" target=\"_blank\" href=\"http://wpa.qq.com/msgrd?v=3&amp;uin=2374149901&amp;site=qq&amp;menu=yes\" class=\"ui-button ui-button-primary ui-button-sm jmod-wpa-qq jmod-consult-stat\" _wpa_qq=\"2374149901\" data-cid=\"320425\" data-csid=\"17690\">]";
        HtmlPage page1=null;
        for (DomElement element : alist) {
            if (str.equals(element.toString())) {
                page1 = element.click();
                System.out.println("获取并点击【QQ咨询】按钮");
            }
        }
        List<Object> byXPath = page1.getByXPath("//*[@id=\"switcher_plogin\"]");
        for (Object xpath : byXPath) {
            System.out.println(xpath.toString());
        }
    }
}
