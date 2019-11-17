package CrawlerTest;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.net.URL;

public class HttpClient {
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
        HtmlPage page = webclient.getPage("https://c.qq.com/");
        System.out.println("获取登录网页");
        DomNodeList<DomElement> alist = page.getElementsByTagName("a");
        String str = "HtmlAnchor[<a href=\"javascript:void(0);\" class=\"btn jmod-index-login\" _stat_click_id=\"5_01\">]";
        HtmlPage page1=null;
        for (DomElement element : alist) {
            if (str.equals(element.toString())) {
                page1 = element.click();
                System.out.println("获取并点击【立即登录】按钮");
            }
        }
        HtmlElement iframeElement = page1.getHtmlElementById("oPopup-loginDialog");
        System.out.println("获取内联框架");
        String url = "https:" + iframeElement.getAttribute("src");
        System.out.println(url);
        WebRequest request = new WebRequest(new URL(url));
        HtmlPage page2 = webclient.getPage(request);
        boolean b = webclient.getAjaxController().processSynchron(page2, request, true);
        System.out.println("异步请求-------------------------"+b+"---------------------------------");
        HtmlForm loginform = page2.getFormByName("loginform");
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
        HtmlPage page3 = button.click();
        System.out.println(page3.asXml());
      /*  System.out.println("获取异步url");
        webclient.waitForBackgroundJavaScript(10000);
        boolean b = webclient.getAjaxController().processSynchron(page2, request, true);
        System.out.println("异步请求-------------------------"+b+"---------------------------------");
        DomElement switcherPlogin = page1.getElementById("switcher_plogin");
        HtmlPage page3 = switcherPlogin.click();
        System.out.println("切换到账号密码登录");*/











       /* WebClient webClient = new WebClient(BrowserVersion.FIREFOX_60);
        webClient.getOptions().setJavaScriptEnabled(false);
        System.out.println(webClient.getOptions().isJavaScriptEnabled());
        HtmlPage page = null;
        HtmlPage pageResponse = null;

        AjaxController ajaxController = new NicelyResynchronizingAjaxController();

        try
        {
            WebRequest request = new WebRequest(new URL("https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=710023101&style=20&s_url=https%3A%2F%2Fc.qq.com%2Fhtml%2Fproxy%2FloginSuccess.html&target=self"));
            webClient.setAjaxController(ajaxController);
            webClient.waitForBackgroundJavaScript(10000);
            page = webClient.getPage(request);
            ajaxController.processSynchron(page, request, true);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        HtmlForm loginform = page.getFormByName("loginform");
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
        HtmlPage page3 = button.click();
        System.out.println(page3.getUrl());
        System.out.println("----------------------------------------------------------------------------------");
        HtmlElement forgetpwd = page.getHtmlElementById("forgetpwd");
        HtmlPage page1 = forgetpwd.click();
        System.out.println(page1.asXml());*/



       /* WebClient webclient = new WebClient(BrowserVersion.CHROME);
        System.out.println("初始化谷歌浏览器");
        webclient.getOptions().setCssEnabled(false);
        System.out.println("禁用css，避免自动二次请求进行渲染");
        webclient.getOptions().setJavaScriptEnabled(true);
        System.out.println("忽略ssl认证");
        webclient.setAjaxController(new NicelyResynchronizingAjaxController());
        System.out.println("支持ajax");
        HtmlPage page = webclient.getPage("https://c.qq.com/");
        System.out.println("获取登录网页");
        DomNodeList<DomElement> alist = page.getElementsByTagName("a");
        String str = "HtmlAnchor[<a href=\"javascript:void(0);\" class=\"btn jmod-index-login\" _stat_click_id=\"5_01\">]";
        HtmlPage page1=null;
        for (DomElement element : alist) {
            if (str.equals(element.toString())) {
                page1 = element.click();
                System.out.println("获取并点击【立即登录】按钮");
            }
        }
        HtmlElement iframeElement = page1.getHtmlElementById("oPopup-loginDialog");
        System.out.println("获取内联框架");
        String url = "https:" + iframeElement.getAttribute("src");
        WebRequest webRequest = new WebRequest(new URL(url));
        webRequest.setHttpMethod(HttpMethod.GET);
        System.out.println("获取异步url");
        AjaxController ajaxController = new NicelyResynchronizingAjaxController();
        webclient.setAjaxController(ajaxController);
        webclient.waitForBackgroundJavaScript(10000);
        boolean b = webclient.getAjaxController().processSynchron(page1, webRequest, true);
        System.out.println(page1.asXml());
        DomElement switcherPlogin = page1.getElementById("switcher_plogin");
        HtmlPage page2 = switcherPlogin.click();
        System.out.println("切换到账号密码登录");
        System.out.println("===============================开始登陆========================================");
        HtmlForm loginform = page2.getFormByName("loginform");
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
        HtmlPage page3 = button.click();
        System.out.println(page3.asXml());
        System.out.println(page3.getUrl());*/
       /* DomNodeList<DomElement> alist = page.getElementsByTagName("a");
        String str = "HtmlAnchor[<a href=\"javascript:void(0);\" class=\"btn jmod-index-login\" _stat_click_id=\"5_01\">]";
        for (DomElement element : alist) {
            if (str.equals(element.toString())) {
                System.out.println("获取【立即登录】按钮");
        // 获取指定网页实体
        HtmlPage page = (HtmlPage) webclient.getPage("https://c.qq.com/");
        //通过div找到按钮
        DomNodeList<DomElement> elements = page.getElementsByTagName("div");
        for (int i = 0; i < elements.size(); i++) {
            String str = "HtmlDivision[<div class=\"mod-userbox-login\">]";
            DomElement element = elements.get(i);
            String elementStr = element.toString();
            boolean flag = str.equals(elementStr);
            if (flag) {
                Iterable<DomElement> childElements = element.getChildElements();
                Iterator<DomElement> iterator = childElements.iterator();
                while (iterator.hasNext()) {
                    DomElement element1 = iterator.next();
                    HtmlPage page1 = element1.click();
                    System.out.println(page1.getUrl());
                }
    }
}

               /* for (int j = 0; j < inputPage.getElementsByTagName("a").size(); j++) {
                    System.out.println(inputPage.getElementsByTagName("a").get(j));
                }*/
                /*HtmlInput btn = (HtmlInput) page.getHtmlElementById("switcher_plogin");
                btn.click();*/
                /*HtmlInput user = inputPage.getHtmlElementById("u");
                user.setValueAttribute("2019559678");
                HtmlInput pwd = inputPage.getHtmlElementById("p");
                pwd.setValueAttribute("zgx@0023");
                // 获取搜索按钮
                HtmlInput btn = (HtmlInput) page.getHtmlElementById("login_button");
                // “点击” 登录
                HtmlPage newPage = btn.click();
                System.out.println(newPage);*/

       /* DomNodeList<DomElement> testElements = page.getElementsByTagName("a");
        for (int j = 0; j < testElements.size(); j++) {
            String str="HtmlAnchor[<a href=\"/TxZone\" _stat_click_id=\"1_94\" target=\"_blank\" class=\"mod-nav-item\">]";
            DomElement element = testElements.get(j);
            String elementStr = element.toString();
            boolean flag = str.equals(elementStr);
            if (flag) {

                HtmlPage page1 = element.click();
                System.out.println("点击【立即登录】");
                FrameWindow login_frame = page1.getFrameByName("login_frame");
                System.out.println("获取iframe");

            }

        }

       https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=710023101&style=20&s_url=https%3A%2F%2Fc.qq.com%2Fhtml%2Fproxy%2FloginSuccess.html&target=self*/
      /*  */

        }

    }




