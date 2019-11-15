package test;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.IOException;
import java.net.URL;

public class HttpClient {
    public static void main(String[] args) throws IOException {
        WebClient webclient = new WebClient(BrowserVersion.CHROME);
        System.out.println("初始化谷歌浏览器");
        webclient.getOptions().setCssEnabled(false);
        System.out.println("禁用css，避免自动二次请求进行渲染");
        webclient.getOptions().setJavaScriptEnabled(true);
        System.out.println("忽略ssl认证");
        webclient.setAjaxController(new NicelyResynchronizingAjaxController());
        System.out.println("支持ajax");
        HtmlPage page = webclient.getPage("https://c.qq.com/");
        System.out.println("获取登录网页");
        HtmlElement iframeElement = page.getHtmlElementById("oPopup-loginDialog");
        System.out.println("获取内联框架");
        String url = String.format("https:" + iframeElement.getAttribute("src"));
        WebRequest webRequest = new WebRequest(new URL(url));
        webRequest.setHttpMethod(HttpMethod.GET);
        System.out.println("获取异步url");
        boolean b = webclient.getAjaxController().processSynchron(page, webRequest, true);
        System.out.println(b);
       /* DomNodeList<DomElement> alist = page.getElementsByTagName("a");
        String str = "HtmlAnchor[<a href=\"javascript:void(0);\" class=\"btn jmod-index-login\" _stat_click_id=\"5_01\">]";
        for (DomElement element : alist) {
            if (str.equals(element.toString())) {
                System.out.println("获取【立即登录】按钮");
                HtmlPage page1 = element.click();
                System.out.println("点击【立即登录】");
                FrameWindow login_frame = page1.getFrameByName("login_frame");
                System.out.println("获取iframe");

            }
        }

       https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=710023101&style=20&s_url=https%3A%2F%2Fc.qq.com%2Fhtml%2Fproxy%2FloginSuccess.html&target=self*/
        DomElement switcherPlogin = page.getElementById("switcher_plogin");
        HtmlPage page1 = switcherPlogin.click();
        System.out.println("切换到账号密码登录");
        System.out.println("===============================开始登陆========================================");
        HtmlForm loginform = page1.getFormByName("loginform");
        System.out.println("获取表单");
        HtmlTextInput user = loginform.getInputByName("u");
        HtmlPasswordInput passwd = loginform.getInputByName("p");
        HtmlSubmitInput button = loginform.getInputByValue("登 录");
        //元素赋值
        user.setValueAttribute("2019559678");
        passwd.setValueAttribute("zgx@0023");
        System.out.println("用户名："+user.getValueAttribute()+"密码："+passwd.getValueAttribute()+"");
        //5.提交
        System.out.println("点击【"+button.getValueAttribute()+"】");
        HtmlPage page2 = button.click();
        System.out.println(page2.asXml());
        System.out.println(page2.getUrl());
    }
}



